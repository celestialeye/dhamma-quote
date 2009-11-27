package dhammaquote;

import com.google.wave.api.ProfileServlet;

public class Dhamma_QuoteProfile extends ProfileServlet {

	private static final long serialVersionUID = 292349837297292983L;

	@Override
	public String getRobotName() {
		return "Dhamma Quote";
	}

	@Override
	public String getRobotAvatarUrl() {
		return "http://dhamma-quote.appspot.com/assets/quote.png";
	}

	@Override
	public String getRobotProfilePageUrl() {
		return "http://dhamma-quote.appspot.com/";
	}
}