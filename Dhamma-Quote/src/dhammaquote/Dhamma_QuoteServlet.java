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

	
	private static ArrayList<ArrayList<String>> quotes = new ArrayList<ArrayList<String>>();
	private static ArrayList<String> pairs = new ArrayList<String>();
	private static ArrayList<String> heedfulness = new ArrayList<String>();

	@Override
	public void processEvents(RobotMessageBundle bundle) {
		Wavelet wavelet = bundle.getWavelet();

		if (bundle.wasSelfAdded()) {
			Blip blip = wavelet.appendBlip();
			TextView textView = blip.getDocument();
			textView
					.append("Hi! I'm Dhamma Quote bot!\n\n"
							+ "I am happy to serve the Buddha's wise words and hope they are beneficial to you. "
							+ "To get a quote, simply type one of the following within your text:\n\n"
							+ "<pairs>\n"
							+ "<heedfulness>\n\n"
							+ "I am created by Peter Wu, and for more Buddhism wisdom and meditation tips,"
							+ "please visit http://www.yellowrobe.com\n\n"
							+ "Last updated on 11/27/2009");

			Blip blip2 = wavelet.appendBlip();
			TextView textView2 = blip2.getDocument();
			try {
				readInQuotes();
			} catch (FileNotFoundException e1) {
			} catch (IOException e) {
			}
			textView2
					.append("Imported Dhammapada Book I - Yamakavagga (Pairs):"
							+ pairs.size() + " quotes\n");
			textView2
					.append("Imported Dhammapada Book II - Appamadavagga (Heedfulness):"
							+ heedfulness.size() + " quotes\n");

			/*
			 * for (int i = 0; i < pairs.size(); i++) { textView.append("\n" +
			 * pairs.get(i)); }
			 */

		}

		for (Event e : bundle.getEvents()) {
			if (e.getType() == EventType.BLIP_SUBMITTED) {

				Blip currentBlip = e.getBlip();
				Blip blip = wavelet.appendBlip();
				TextView textView = blip.getDocument();
				TextView currentTextView = currentBlip.getDocument();

				String str = currentTextView.getText();
				Random r = new Random();

				// textView.append("processing");
				if (str.indexOf("<pairs>") != -1) {
					int index = r.nextInt(pairs.size() - 1);
					// textView.append("retrieving quote #" + index);
					textView.append("Dhammapada Book I - Yamakavagga (Pairs) #"
							+ index + "\n\n" + pairs.get(index));
				} else if (str.indexOf("<heedfulness>") != -1) {
					int index = r.nextInt(heedfulness.size() - 1);
					// textView.append("retrieving quote #" + index);
					textView
							.append("Dhammapada Book II - Appamadavagga (Heedfulness) #"
									+ index + "\n\n" + heedfulness.get(index));
				} else {
					textView.append("\nI can't understand that - " + "text: "
							+ str);
				}
			}
		}

	}

	private void readInQuotes() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"pairs.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				pairs.add(line);
			}
			reader.close();

		} catch (MalformedURLException e) {
			// ...
		} catch (IOException e) {
			// ...
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"heedfulness.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				heedfulness.add(line);
			}
			reader.close();

		} catch (MalformedURLException e) {
			// ...
		} catch (IOException e) {
			// ...
		}
	}

}
