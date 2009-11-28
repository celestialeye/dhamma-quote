package dhammaquote;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import com.google.wave.api.*;

public class Dhamma_QuoteServlet extends AbstractRobotServlet {

	private static final long serialVersionUID = 292349837297292983L;

	private ArrayList<ArrayList<String>> quotes = new ArrayList<ArrayList<String>>();
	private ArrayList<String> books = new ArrayList<String>();
	private ArrayList<String> booknames = new ArrayList<String>();

	private String language = "english";
	private String updated = "11/28/2009";

	@Override
	public void processEvents(RobotMessageBundle bundle) {
		Wavelet wavelet = bundle.getWavelet();

		if (bundle.wasSelfAdded()) {
			populateBookList();
			greeting(wavelet);
			readBooks(wavelet);
			// selfTest(wavelet);
			// printAllQuotes(wavelet);
		}

		for (Event e : bundle.getEvents()) {
			if (e.getType() == EventType.BLIP_SUBMITTED) {
				Blip currentBlip = e.getBlip();
				TextView currentTextView = currentBlip.getDocument();
				// currentTextView.append("\n\nReceived ya words");
				if (quotes.size() == 0) {
					// currentTextView.append("\n\nRe-populating words");
					// fix stupid Google Wave bot bug that makes bot have
					// amnesia problem?
					populateBookList();
					readBooks(wavelet);
				}
				processText(wavelet, currentTextView);
			}
		}

	}

	private void populateBookList() {
		books.add("pairs");
		booknames.add("Dhammapada Book I - Yamakavagga (Pairs)");
		books.add("heedfulness");
		booknames.add("Dhammapada Book II - Appamadavagga (Heedfulness)");
		books.add("mind");
		booknames.add("Dhammapada Book III - Cittavagga (The Mind)");
		books.add("flowers");
		booknames.add("Dhammapada Book IV - Pupphavagga (Flowers)");
		books.add("fool");
		booknames.add("Dhammapada Book V - Balavagga (The Fool)");
		books.add("wise");
		booknames.add("Dhammapada Book VI - Panditavagga (The Wise)");
		books.add("arahant");
		booknames
				.add("Dhammapada Book VII - Arahantavagga (The Arahant or Perfected One)");
		books.add("thousands");
		booknames.add("Dhammapada Book VIII - Sahassavagga (The Thousands)");
		books.add("evil");
		booknames.add("Dhammapada Book IX - Papavagga (Evil)");
		books.add("violence");
		booknames.add("Dhammapada Book X - Dandavagga (Violence)");
		books.add("oldage");
		booknames.add("Dhammapada Book XI - Jaravagga (Old Age)");
		books.add("self");
		booknames.add("Dhammapada Book XII - Attavagga (The Self)");
		books.add("world");
		booknames.add("Dhammapada Book XIII - Lokavagga (The World)");
		books.add("buddha");
		booknames.add("Dhammapada Book XIV - Buddhavagga (The Buddha)");
		books.add("happiness");
		booknames.add("Dhammapada Book XV - Sukhavagga (Happiness)");
		books.add("affection");
		booknames.add("Dhammapada Book XVI - Piyavagga (Affection)");
		books.add("anger");
		booknames.add("Dhammapada Book XVII - Kodhavagga (Anger)");
		books.add("impurity");
		booknames.add("Dhammapada Book XVIII - Malavagga (Impurity)");
		books.add("just");
		booknames.add("Dhammapada Book XIX - Dhammatthavagga (The Just)");
		books.add("path");
		booknames.add("Dhammapada Book XX - Maggavagga (The Path)");
		books.add("misc");
		booknames.add("Dhammapada Book XXI - Pakinnakavagga (Miscellaneous)");
		books.add("hell");
		booknames.add("Dhammapada Book XXII - Nirayavagga (Hell)");
		books.add("elephant");
		booknames.add("Dhammapada Book XXIII - Nagavagga (The Elephant)");
		books.add("craving");
		booknames.add("Dhammapada Book XXIV - Tanhavagga (Craving)");
		books.add("monk");
		booknames.add("Dhammapada Book XXV - Bhikkhuvagga (The Monk)");
		books.add("holyman");
		booknames.add("Dhammapada Book XXVI - Brahmanavagga (The Holy Man)");
	}

	private void greeting(Wavelet wavelet) {
		Blip blip = wavelet.appendBlip();
		TextView textView = blip.getDocument();
		textView
				.append("Hi! I'm DQ the Dhamma Quote bot!\n\n"
						+ "I am happy to serve the Buddha's wise words and hope they are beneficial to you. "
						+ "To get a quote, simply type one of the following within your text:\n\n");

		for (int i = 0; i < books.size(); i++) {
			textView.appendStyledText(createHeader("!" + books.get(i)));
			textView.append(", ");
		}

		// additional commands
		textView.appendStyledText(createHeader("!random"));

		textView
				.append("\n\n"
						+ "Do you know I can speak other languages too? Tell me which language to speak by typing"
						+ " (default english):\n\n)");

		textView.appendStyledText(createHeader("!english"));
		textView.append("\n");
		textView.appendStyledText(createHeader("!chinese"));
		textView
				.append("\n\n"
						+ "I am created by Peter Wu (Yi-Lei Wu), and for more Buddhism wisdom and meditation tips,"
						+ "please visit http://www.yellowrobe.com\n\n"
						+ "To read this greeting again, type: ");
		textView.appendStyledText(createHeader("!greeting"));
		textView.append("\n\nLast updated on " + updated);
	}

	private void greeting_chinese(Wavelet wavelet) {
		Blip blip = wavelet.appendBlip();
		TextView textView = blip.getDocument();
		textView.append("Hi! 大家好，我是佛偈機器人! 小名叫 DQ (Dhamma Quote)\n\n"
				+ "我很高興在這裡為大家提供釋迦摩尼佛的佛偈，并希望能對您有幫助。"
				+ "欲獲取一段佛偈，請輸入以下任何一個指令在您的消息裏面:\n\n");

		for (int i = 0; i < books.size(); i++) {
			textView.appendStyledText(createHeader("!" + books.get(i)));
			textView.append(", ");
		}

		// additional commands
		textView.appendStyledText(createHeader("!random"));

		textView.append("\n\n" + "您知道我還會說其他語言嗎? 告訴我該說什麽語言好呢?"
				+ " (默認 english):\n\n)");
		textView.appendStyledText(createHeader("!english"));
		textView.append("\n");
		textView.appendStyledText(createHeader("!chinese"));

		textView.append("\n\n"
				+ "我的創造者是 Peter Wu (Yi-Lei Wu)， 要獲取更多的佛教智慧和禪修信息，"
				+ "請訪問 http://www.yellowrobe.com\n\n" + "要再閱讀這個信息一次，請輸入: ");
		textView.appendStyledText(createHeader("!greeting"));
		textView.append("\n\n最後更新于 " + updated);
	}

	private void readBooks(Wavelet wavelet) {
		// Blip blip = wavelet.appendBlip();
		// TextView textView = blip.getDocument();
		for (int i = 0; i < books.size(); i++) {
			String filename = books.get(i) + ".txt";
			// textView.append("Reading file " + filename + "\n");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						filename));
				String line;
				quotes.add(new ArrayList<String>());
				while ((line = reader.readLine()) != null) {
					quotes.get(i).add(line);
				}
				reader.close();

			} catch (MalformedURLException e) {
				// textView.append("MalformedURLException caught\n");
			} catch (IOException e) {
				// textView.append("IOException caught\n");
			}
		}

	}

	private void selfTest(Wavelet wavelet) {
		Blip blip = wavelet.appendBlip();
		TextView textView = blip.getDocument();
		for (int i = 0; i < quotes.size(); i++) {
			Random r = new Random();
			int index = r.nextInt(quotes.get(i).size() - 1);
			textView.append(quotes.get(i).get(index) + "\n");
		}
	}

	private void printAllQuotes(Wavelet wavelet) {
		Blip blip = wavelet.appendBlip();
		TextView textView = blip.getDocument();
		for (int i = 0; i < quotes.size(); i++) {
			for (int j = 0; j < quotes.get(i).size(); j++) {
				textView.append(quotes.get(i).get(j) + "\n");
			}
		}
	}

	private void getBookList(TextView currentTextView) {
		currentTextView
				.append("\n\nHmm... my quotes are gone? Checking status...");
		currentTextView.append("\n# of books in quotes: " + quotes.size()
				+ "\n");
		currentTextView.append("\n# of books in books: " + books.size() + "\n");
		currentTextView.append("\n# of books in booknames: " + booknames.size()
				+ "\n");
		for (int j = 0; j < quotes.size(); j++) {
			currentTextView.append(booknames.get(j) + ":"
					+ quotes.get(j).size() + " quotes\n");
		}
	}

	private void processText(Wavelet wavelet, TextView currentTextView) {
		int hit = 0;
		String str = currentTextView.getText();

		if (str.indexOf("!english") != -1) {
			language = "english";
			currentTextView.append("\n\n" + "Yes?");
		} else if (str.indexOf("!chinese") != -1) {
			language = "chinese";
			currentTextView.append("\n\n" + "是嗎?");
		}

		if (str.indexOf("!random") != -1) {
			Random r = new Random();
			int index = r.nextInt(quotes.size() - 1);
			int index2 = r.nextInt(quotes.get(index).size() - 1);
			currentTextView.append("\n\n" + booknames.get(index) + " #"
					+ index2 + "\n\n" + quotes.get(index).get(index2));
		}

		if (str.indexOf("!greeting") != -1) {
			if (language.equals("english")) {
				greeting(wavelet);
			} else if (language.equals("chinese")) {
				greeting_chinese(wavelet);
			}
		}

		for (int i = 0; i < books.size(); i++) {
			String match = "!" + books.get(i);
			if (str.indexOf(match) != -1) {
				Random r = new Random();
				// Blip blip = wavelet.appendBlip();
				// TextView textView = blip.getDocument();
				if ((quotes.get(i).size() - 1) < 0) {
					// getBookList(currentTextView);
				} else {
					int index = r.nextInt(quotes.get(i).size() - 1);
					currentTextView.append("\n\n");
					currentTextView.appendStyledText(createHeader(booknames
							.get(i)
							+ " #" + index));
					currentTextView.append("\n\n" + quotes.get(i).get(index));
					hit = 1;
				}
				// break; // this can prevent event bubble bug, but won't be
				// able to insert more than 2 quotes
			}
		}
		if (hit == 0) {
			// Blip blip = wavelet.appendBlip();
			// TextView textView = blip.getDocument();
			// currentTextView
			// .append("\n\nSorry, I can't understand that command");
			// getBookList(currentTextView);
		}
	}

	public StyledText createRule() {
		return new StyledText("-----------------", StyleType.BOLD);
	}

	public StyledText createHeader(String header) {
		return new StyledText(header, StyleType.BOLD);
	}
}
