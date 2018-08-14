package com.ocelot.mod.game;

public class GameCrashException extends RuntimeException {

	public GameCrashException(String message) {
		super(message);
	}

	public GameCrashException(Throwable cause) {
		super(cause);
	}

	public GameCrashException(String message, Throwable cause) {
		super(message, cause);
	}
}