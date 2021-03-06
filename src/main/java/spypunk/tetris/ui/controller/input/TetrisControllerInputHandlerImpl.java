/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.tetris.ui.controller.input;

import java.awt.event.KeyEvent;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.collections4.ListUtils;

import com.google.common.collect.Maps;

import spypunk.tetris.model.Movement;
import spypunk.tetris.ui.controller.command.TetrisControllerCommand;
import spypunk.tetris.ui.factory.TetrisControllerCommandFactory;

@Singleton
public class TetrisControllerInputHandlerImpl implements TetrisControllerInputHandler {

    private final BitSet pressedKeysBitSet = new BitSet();

    private final BitSet releasedKeysBitSet = new BitSet();

    private final Map<Integer, Supplier<TetrisControllerCommand>> pressedKeyCodesHandlers = Maps.newHashMap();

    private final Map<Integer, Supplier<TetrisControllerCommand>> releasedKeyCodesHandlers = Maps.newHashMap();

    @Inject
    public TetrisControllerInputHandlerImpl(final TetrisControllerCommandFactory tetrisControllerCommandFactory) {
        pressedKeyCodesHandlers.put(KeyEvent.VK_LEFT,
            () -> tetrisControllerCommandFactory.createMovementTetrisControllerCommand(Movement.LEFT));

        pressedKeyCodesHandlers.put(KeyEvent.VK_RIGHT,
            () -> tetrisControllerCommandFactory.createMovementTetrisControllerCommand(Movement.RIGHT));

        pressedKeyCodesHandlers.put(KeyEvent.VK_DOWN,
            () -> tetrisControllerCommandFactory.createMovementTetrisControllerCommand(Movement.DOWN));

        releasedKeyCodesHandlers.put(KeyEvent.VK_SPACE,
            tetrisControllerCommandFactory::createNewGameTetrisControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_P, tetrisControllerCommandFactory::createPauseTetrisControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_UP,
            () -> tetrisControllerCommandFactory.createMovementTetrisControllerCommand(Movement.ROTATE_CW));

        releasedKeyCodesHandlers.put(KeyEvent.VK_M, tetrisControllerCommandFactory::createMuteTetrisControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_PAGE_UP,
            tetrisControllerCommandFactory::createIncreaseVolumeTetrisControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_PAGE_DOWN,
            tetrisControllerCommandFactory::createDecreaseVolumeTetrisControllerCommand);

        releasedKeyCodesHandlers.put(KeyEvent.VK_CONTROL,
            tetrisControllerCommandFactory::createHardDropTetrisControllerCommand);
    }

    @Override
    public void onKeyPressed(final int keyCode) {
        pressedKeysBitSet.set(keyCode);
    }

    @Override
    public void onKeyReleased(final int keyCode) {
        releasedKeysBitSet.set(keyCode);
    }

    @Override
    public List<TetrisControllerCommand> handleInputs() {
        return ListUtils.union(getCommandsFromKeys(pressedKeysBitSet, pressedKeyCodesHandlers),
            getCommandsFromKeys(releasedKeysBitSet, releasedKeyCodesHandlers));
    }

    @Override
    public void reset() {
        pressedKeysBitSet.clear();
        releasedKeysBitSet.clear();
    }

    private List<TetrisControllerCommand> getCommandsFromKeys(final BitSet bitSet,
            final Map<Integer, Supplier<TetrisControllerCommand>> keyCodesHandlers) {

        if (bitSet.isEmpty()) {
            return Collections.emptyList();
        }

        return keyCodesHandlers.keySet().stream().filter(keyCode -> isKeyTriggered(keyCode, bitSet))
                .map(keyCode -> getCommandFromKeyCode(keyCodesHandlers, keyCode)).collect(Collectors.toList());
    }

    private TetrisControllerCommand getCommandFromKeyCode(
            final Map<Integer, Supplier<TetrisControllerCommand>> keyCodesHandlers, final Integer keyCode) {
        return keyCodesHandlers.get(keyCode).get();
    }

    private boolean isKeyTriggered(final int keyCode, final BitSet bitSet) {
        return bitSet.get(keyCode);
    }
}
