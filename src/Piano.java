import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.sound.midi.*;

/**
 * Implements a simulated piano with 36 keys.
 */
public class Piano extends JPanel {
	// DO NOT MODIFY THESE CONSTANTS
	public static int START_PITCH = 48;
	public static int WHITE_KEY_WIDTH = 40;
	public static int BLACK_KEY_WIDTH = WHITE_KEY_WIDTH/2;
	public static int WHITE_KEY_HEIGHT = 200;
	public static int BLACK_KEY_HEIGHT = WHITE_KEY_HEIGHT/2;
	public static int NUM_WHITE_KEYS_PER_OCTAVE = 7;
	public static int NUM_OCTAVES = 3;
	public static int NUM_WHITE_KEYS = NUM_WHITE_KEYS_PER_OCTAVE * NUM_OCTAVES;
	public static int NUM_KEYS = 36;
	public static int WIDTH = NUM_WHITE_KEYS * WHITE_KEY_WIDTH;
	public static int HEIGHT = WHITE_KEY_HEIGHT;
	public static int[] BLACK_KEYS = {1,3,6,8,10};
		
	private java.util.List<Key> _keys = new ArrayList<>();
	private Receiver _receiver;
	private PianoMouseListener _mouseListener;


	/**
	 * Returns the list of keys in the piano.
	 * @return the list of keys.
	 */
	public java.util.List<Key> getKeys () {
		return _keys;
	}

	/**
	 * Sets the MIDI receiver of the piano to the specified value.
	 * @param receiver the MIDI receiver 
	 */
	public void setReceiver (Receiver receiver) {
		_receiver = receiver;
	}

	/**
	 * Returns the current MIDI receiver of the piano.
	 * @return the current MIDI receiver 
	 */
	public Receiver getReceiver () {
		return _receiver;
	}

	// DO NOT MODIFY THIS METHOD.
	/**
	 * @param receiver the MIDI receiver to use in the piano.
	 */
	public Piano (Receiver receiver) {
		// Some Swing setup stuff; don't worry too much about it.
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setReceiver(receiver);
		_mouseListener = new PianoMouseListener(_keys);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		makeKeys();
	}

	/**
	 * Returns the PianoMouseListener associated with the piano.
	 * @return the PianoMouseListener associated with the piano.
	 */
	public PianoMouseListener getMouseListener () {
		return _mouseListener;
	}

	// TODO: implement this method. You should create and use several helper methods to do so.
	/**
	 * Instantiate all the Key objects with their correct polygons and pitches, and
	 * add them to the _keys array.
	 */
	private void makeKeys () {
		int keyDrawPos = 0;
		Polygon polygon;
		for(int currentKey = 0; keyDrawPos < 860; currentKey++) {
			if (true) { //isBlackKey(currentKey)){
				int[] xCoords = new int[]{
						keyDrawPos - (BLACK_KEY_WIDTH / 2),
						keyDrawPos - (BLACK_KEY_WIDTH / 2),
						keyDrawPos + (BLACK_KEY_WIDTH / 2),
						keyDrawPos + (BLACK_KEY_WIDTH / 2)
				};
				int[] yCoords = new int[]{
						0,
						BLACK_KEY_HEIGHT,
						BLACK_KEY_HEIGHT,
						0
				};
				keyDrawPos += WHITE_KEY_WIDTH;
				polygon = new Polygon(xCoords, yCoords, xCoords.length);
			} else { // TODO: Fix coordinate order so new array can easily be made for white notes
				int[] xCoords = new int[8];
				int[] yCoords = new int[8];
				int numOfCoordinates = 4;

				xCoords[0] = keyDrawPos;
				xCoords[1] = keyDrawPos + WHITE_KEY_WIDTH;

				yCoords[0] = WHITE_KEY_HEIGHT;
				yCoords[1] = WHITE_KEY_HEIGHT;

				if (isBlackKey((currentKey + 1) % 12)) {
					xCoords[2] = keyDrawPos + WHITE_KEY_WIDTH;
					xCoords[3] = keyDrawPos + WHITE_KEY_WIDTH - BLACK_KEY_WIDTH / 2;
					xCoords[4] = keyDrawPos + WHITE_KEY_WIDTH - BLACK_KEY_WIDTH / 2;
					numOfCoordinates = numOfCoordinates + 2;
				} else {
					xCoords[2] = keyDrawPos + WHITE_KEY_WIDTH;
				}
				if (isBlackKey((currentKey + 11) % 12)) {
					xCoords[3 + (numOfCoordinates - 4)] = keyDrawPos + BLACK_KEY_WIDTH / 2;
					xCoords[4 + (numOfCoordinates - 4)] = keyDrawPos + BLACK_KEY_WIDTH / 2;
					xCoords[5 + (numOfCoordinates - 4)] = keyDrawPos;

					yCoords[3 + (numOfCoordinates - 4)] = keyDrawPos + BLACK_KEY_WIDTH / 2;
					yCoords[4 + (numOfCoordinates - 4)] = keyDrawPos + BLACK_KEY_WIDTH / 2;
					yCoords[5 + (numOfCoordinates - 4)] = keyDrawPos;

					numOfCoordinates = numOfCoordinates + 2;
				} else {
					xCoords[3 + (numOfCoordinates - 4)] = keyDrawPos;
				}


				polygon = new Polygon(Arrays.copyOf(xCoords, numOfCoordinates), Arrays.copyOf(yCoords, numOfCoordinates), numOfCoordinates);
				keyDrawPos += WHITE_KEY_WIDTH;
			}
			// Just as an example, this draws the left-most black key at its proper position.
			Key key = new Key(polygon, START_PITCH, this);

			// Add this key to the list of keys so that it gets painted.
			_keys.add(key);
		}
	}

	/**
	 * returns true if the given key number corresponds to a black key, false otherwise
	 * @param key The number of the key being checked
	 */
	public boolean isBlackKey(int key) {
		key = key % 12;
		for (int blackKey : BLACK_KEYS) {
			if (key == blackKey) {
				return true;
			}
		}
		return false;
	}

	// DO NOT MODIFY THIS METHOD.
	@Override
	/**
	 * Paints the piano and all its constituent keys.
	 * @param g the Graphics object to use for painting.
	 */
	public void paint (Graphics g) {
		// Delegates to all the individual keys to draw themselves.
		for (Key key: _keys) {
			key.paint(g);
		}
	}
}
