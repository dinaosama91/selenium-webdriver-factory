package com.selenium.testautomation.core.configuration;

public class BrowserProfiles {

	  private static String DOWNLOAD_DRECTORY = ConfigurationFiles.getpropertyValue("downloadDirPath");
	  private static String SCREENCASTIFY_PATH = ConfigurationFiles.getpropertyValue("screenCastifyPath");
	  private static DesiredCapabilities capabilities;
	  protected static DesiredCapabilities chromeProfile() {
	    capabilities = DesiredCapabilities.chrome();
	    ChromeOptions options = new ChromeOptions();
	    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	    chromePrefs.put("profile.default_content_settings.popups", 0);
	    chromePrefs.put("download.default_directory", DOWNLOAD_DRECTORY);
	    chromePrefs.put("credentials_enable_service", false);
	    chromePrefs.put("profile.password_manager_enabled", false);
	    options.addArguments("disable-notifications");
	    options.setExperimentalOption("prefs", chromePrefs);
	    options.addArguments("start-maximized");
	    options.addArguments("disable-infobars");
	    options.addArguments("--allow-http-screen-capture");
	    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	    return capabilities;
	  }

	  protected static DesiredCapabilities firefoxprofile() {
	    capabilities = DesiredCapabilities.firefox();
	    FirefoxProfile firefoxProfile = new FirefoxProfile();
	    FirefoxOptions options = new FirefoxOptions();
	    firefoxProfile.setPreference("dom.popup_maximum", 0);
	    firefoxProfile.setPreference("privacy.popups.showBrowserMessage", false);
	    firefoxProfile.setPreference("browser.download.folderList", 2);
	    firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
	    firefoxProfile.setPreference("browser.download.dir", DOWNLOAD_DRECTORY);
	    firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
	    firefoxProfile.setPreference("browser. download. manager. focusWhenStarting", false);
	    firefoxProfile.setPreference("browser. download. manager. showAlertOnComplete", false);
	    firefoxProfile.setPreference("browser. helperApps. alwaysAsk. force", false);
	    return options.setProfile(firefoxProfile).addTo(capabilities);

	  }

}
