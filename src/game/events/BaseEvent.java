package game.events;

import game.Game;

/**
 * @author ice1000
 */

public interface BaseEvent {
	void happen(Game game, String extra);
}