package com.app.service;

import java.util.ArrayList;
import java.util.List;

import com.app.model.rss.Channel;
import com.app.model.rss.Feed;
import com.app.model.rss.Guid;
import com.app.model.rss.Item;

public class RSSFeedTestData {

	public static List<Item> items() {
		Item item = new Item();
		item.setTitle("Topcoder - Notifications Popup");
		item.setDescription("<ul>\n" + 
				"	<li>Implement the notification feature to new navigation based on provided design</li>\n" + 
				"	<li>Call the API endpoint to get notifications</li>\n" + 
				"	<li>Show alert when user have a new notification</li>\n" + 
				"</ul>\n" + 
				"<br />\n" + 
				"<strong>Project Background</strong><br />\n" + 
				"Currently, we deployed a new navigation to TopCoder site, as part of new navigation design we have Community Notifications. This will show notifications to members from admins, events and challenges progression. <br />\n" + 
				"<br />\n" + 
				"In this challenge we need you help to implement the Notifications Popup.<br />\n" + 
				"<br />\n" + 
				"<strong>Technology Stack</strong>\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>N...</li></ul><br /><div>Platforms: NodeJS</div><div>Technologies: Apache Kafka / HTML5 / ReactJS / SCSS</div><div>Prize: 900 (600)</div><div>Registration Period: 2020-01-20 21:34 +00:00 - 2020-01-23 21:34 +00:00</div><div>Open for registration: No </div><div>Submissions Due: 2020-01-25 22:18 +00:00</div><div>Type: Code / develop</div>");
		item.setLink("https://www.topcoder.com/challenge-details/30113151/?type=develop");
		item.setGuid(new Guid("false", "30113151"));
		item.setPubDate("Mon, 20 Jan 2020 21:34:00 GMT");
		Item item2 = new Item();
		item2.setTitle("LSF to Nextflow Refactoring F2F - Augustus");
		item2.setDescription("<p>Topcoder is working with a company that is using a High Performance Computing (HPC) technology known as <a href=\"https://en.wikipedia.org/wiki/Platform_LSF\">LSF</a> to perform structural annotation of a genome.  This technology allows users to parallelize work across a cluster of Linux servers.  In this case, breaking up a set of fasta files into slices and processing the individual part of the files in parallel.  </p>\n" + 
				" \n" + 
				"\n" + 
				"<p>The task for this challenge is to remove the LSF dependency from the attached code:  LSF_manyconc.py and convert functionality to a fra...</p><br /><div>Platforms: AWS / EC2 / Linux</div><div>Technologies: Perl / Python</div><div>Prize: 750 (750)</div><div>Registration Period: 2020-01-20 19:59 +00:00 - 2020-01-30 19:59 +00:00</div><div>Open for registration: Yes</div><div>Submissions Due: 2020-01-30 20:02 +00:00</div><div>Type: First2Finish / develop</div>");
		item2.setLink("https://www.topcoder.com/challenge-details/30113108/?type=develop");
		item2.setGuid(new Guid("false", "30113108"));
		item2.setPubDate("Mon, 20 Jan 2020 19:59:00 GMT");
		Item item3 = new Item();
		item3.setTitle("Eaton UI and functional modification");
		item3.setDescription("<strong>(1) Remove the orange color outline</strong><br />\n" + 
				"On clicking on icons, UIs like Icon and button gets selected and covered with an outline of orange color which should be avoided<br />\n" + 
				"<br />\n" + 
				"Tapping on some component used to make it selected(covered with a yellowish orange outline) which was not a pleasant look to the user as per UI/UX standards. Example: If you look into this image, button: “Full Set” is outlined with yellow border which happens on tapping some component.<br />\n" + 
				"<br />\n" + 
				" \n" + 
				"<p><strong>(2) Fix the issue that more than one tabs ar...</strong></p><br /><div>Platforms: Android</div><div>Technologies: Apache Cordova</div><div>Prize: 150 (150)</div><div>Registration Period: 2020-01-20 06:15 +00:00 - 2020-01-30 06:15 +00:00</div><div>Open for registration: Yes</div><div>Submissions Due: 2020-01-30 06:35 +00:00</div><div>Type: First2Finish / develop</div>");
		item3.setLink("https://www.topcoder.com/challenge-details/30113097/?type=develop");
		item3.setGuid(new Guid("false", "30113097"));
		item3.setPubDate("Mon, 20 Jan 2020 06:15:00 GMT");
		Item item4 = new Item();
		item4.setTitle("Eaton Edit Panel update");
		item4.setDescription("1) Edit panel name still not closing in one go in case of click of cancel button. (IOS)<br />\n" + 
				"<br />\n" + 
				"2) On click of disabled &quot;ok&quot; button, UI moves.  (IOS)<br />\n" + 
				"<br />\n" + 
				"Please refer this video for both above https://drive.google.com/a/appirio.com/file/d/1L8q-l6Db4DwJYeUlOw3_MeA6hHgoOWxZ/view?usp=sharing<br />\n" + 
				"<br />\n" + 
				"3) In Android when user is opening the panel the popup / keypad is not opening in one go / smoothly , the screen flickers and moved ups and down, see the video <br />\n" + 
				"    https://drive.google.com/a/appirio.com/file/<br /><div>Platforms: iOS / Android</div><div>Technologies: Apache Cordova</div><div>Prize: 80 (80)</div><div>Registration Period: 2020-01-20 02:33 +00:00 - 2020-01-30 02:33 +00:00</div><div>Open for registration: Yes</div><div>Submissions Due: 2020-01-30 06:35 +00:00</div><div>Type: First2Finish / develop</div>");
		item4.setLink("https://www.topcoder.com/challenge-details/30113046/?type=develop");
		item4.setGuid(new Guid("false", "30113046"));
		item4.setPubDate("Mon, 20 Jan 2020 02:33:00 GMT");
		Item item5 = new Item();
		item5.setTitle("Topcoder  Legacy Challenge Migration Script - Phases Business logic");
		item5.setDescription("Challenge Objectives\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>\n" + 
				"	<p>Update the logic behind the challenge phases based on the requirements below.</p>\n" + 
				"	</li>\n" + 
				"</ul>\n" + 
				"\n" + 
				"Tech Stack\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>\n" + 
				"	<p>Node.js</p>\n" + 
				"	</li>\n" + 
				"	<li>\n" + 
				"	<p>DynamoDB</p>\n" + 
				"	</li>\n" + 
				"	<li>\n" + 
				"	<p>ElasticSearch</p>\n" + 
				"	</li>\n" + 
				"</ul>\n" + 
				"\n" + 
				"Project Background\n" + 
				"\n" + 
				"<p>In this series of challenges, we will build the version 5 (V5) of the challenge API.</p>\n" + 
				"\n" + 
				"Code Access\n" + 
				"\n" + 
				"<h3>Legacy Challenge Migration Script</h3>\n" + 
				"\n" + 
				"<p>Repo: <a href=\"https://github.com/topcoder-platform/legacy-challenge-migration-script\">https://github.com/topcoder-platform/legacy-challenge-migration-script</a></p>\n" + 
				"\n" + 
				"<p>Branch: <strong>develop</strong></p>\n" + 
				" \n" + 
				"\n" + 
				"<p>You may also have to run the following apps locally: <a href=\"https://github.com/topcoder-platform/resources-api\">https://github.com/topcoder...</a></p><br /><div>Platforms: NodeJS</div><div>Technologies: AWS / Docker / Elasticsearch / Node.js / Other</div><div>Prize: 600 (400)</div><div>Registration Period: 2020-01-18 21:48 +00:00 - 2020-01-21 21:51 +00:00</div><div>Open for registration: No </div><div>Submissions Due: 2020-01-21 21:57 +00:00</div><div>Type: Code / develop</div>");
		item5.setLink("https://www.topcoder.com/challenge-details/30113052/?type=develop");
		item5.setGuid(new Guid("false", "30113052"));
		item5.setPubDate("Sat, 18 Jan 2020 21:48:00 GMT");
		Item item6 = new Item();
		item6.setTitle("Ognomy MVP Mobile App React Native UI Challenge");
		item6.setDescription("<strong>CHALLENGE OBJECTIVES</strong>\n" + 
				"<ul>\n" + 
				"	<li>Build a minimal UI for the Ognomy mobile app.</li>\n" + 
				"</ul>\n" + 
				" <br />\n" + 
				"<strong>PROJECT BACKGROUND</strong>\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>We are building an MVP of a mobile app that allows patients to schedule and participate in teleconsultations on their mobile device, and of a web app that allows doctors to view teleconsult schedules and participate in teleconsults from a desktop device.</li>\n" + 
				"	<li>This MVP is the 1st phase of a bigger project, both the mobile app and the web app have a much bigger scope to be done later.</li>\n" + 
				"	<li>For the purpose of this...</li></ul><br /><div>Platforms: Mobile</div><div>Technologies: Android / HTML5 / JavaScript / iOS</div><div>Prize: 1800 (1200)</div><div>Registration Period: 2020-01-18 14:03 +00:00 - 2020-01-23 14:03 +00:00</div><div>Open for registration: No </div><div>Submissions Due: 2020-01-23 14:04 +00:00</div><div>Type: Code / develop</div>");
		item6.setLink("https://www.topcoder.com/challenge-details/30112931/?type=develop");
		item6.setGuid(new Guid("false", "30112931"));
		item6.setPubDate("Sat, 18 Jan 2020 14:03:00 GMT");
		Item item7 = new Item();
		item7.setTitle("Poseidon LPC Payment Backend API Unit and E2E Tests Fixes");
		item7.setDescription("<p>Welcome to “<strong>Poseidon LPC Payment Backend API Unit and E2E Tests Fixes Challenge</strong>”.  In this challenge, we would like to continue building the LPC Payment Backend API.<br />\n" + 
				" <br />\n" + 
				"\n" + 
				"<strong>PROJECT BACKGROUND</strong>\n" + 
				"\n" + 
				"<p>The project objective is to build an SDK for the Loyalty Payment Card (LPC) for our client. This SDK will be used by LPC’s clients to build the LPC mobile app.<br />\n" + 
				"<br />\n" + 
				"So the SDK will provide all required functionalities from authentication to payment processing, reward management, etc. <br />\n" + 
				" <br />\n" + 
				"\n" + 
				"<strong>FRAMEWORK</strong>\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>\n" + 
				"	<p>Node.js 12+</p>...</li></ul><br /><div>Platforms: Other</div><div>Technologies: AWS / Node.js / TypeScript</div><div>Prize: 1800 (1200)</div><div>Registration Period: 2020-01-18 11:10 +00:00 - 2020-01-22 11:11 +00:00</div><div>Open for registration: No </div><div>Submissions Due: 2020-01-23 11:13 +00:00</div><div>Type: Code / develop</div>");
		item7.setLink("https://www.topcoder.com/challenge-details/30113029/?type=develop");
		item7.setGuid(new Guid("false", "30113029"));
		item7.setPubDate("Sat, 18 Jan 2020 11:10:00 GMT");
		Item item8 = new Item();
		item8.setTitle("Amazon Alexa Skill | Polish the skill");
		item8.setDescription("<p><strong>Challenge Objectives</strong></p>\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>\n" + 
				"	<p>Improve an existing Amazon Alexa Skill. Polish it and handle edge case scenarios</p>\n" + 
				"	</li>\n" + 
				"</ul>\n" + 
				" \n" + 
				"\n" + 
				"<p><strong>Project Background</strong></p>\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>\n" + 
				"	<p>The Alexa Skill allows users to book movie tickets for popular movies and </p>\n" + 
				"	</li>\n" + 
				"</ul>\n" + 
				" \n" + 
				"\n" + 
				"<p><strong>Technology Stack</strong></p>\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>\n" + 
				"	<p>Alexa Skills Kit version 2</p>\n" + 
				"	</li>\n" + 
				"	<li>\n" + 
				"	<p>Nodejs</p>\n" + 
				"	</li>\n" + 
				"	<li>\n" + 
				"	<p>AWS (Lambda)</p>\n" + 
				"	</li>\n" + 
				"</ul>\n" + 
				" \n" + 
				"\n" + 
				"<p><strong>Code access</strong></p>\n" + 
				"\n" + 
				"<p>The code will be shared in the contest forum.</p>\n" + 
				" \n" + 
				"\n" + 
				"<p><strong>Individual requirements</strong></p>\n" + 
				"\n" + 
				"<p>Improve an existing Amazon Alexa Skill. Polish it and handle edge case scenarios (Major Requirements)</p>\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>\n" + 
				"	<p>We ...</p></li></ul><br /><div>Platforms: NodeJS</div><div>Technologies: AWS / Node.js</div><div>Prize: 1050 (700)</div><div>Registration Period: 2020-01-18 06:15 +00:00 - 2020-01-20 06:16 +00:00</div><div>Open for registration: No </div><div>Submissions Due: 2020-01-22 18:11 +00:00</div><div>Type: Code / develop</div>");
		item8.setLink("https://www.topcoder.com/challenge-details/30113048/?type=develop");
		item8.setGuid(new Guid("false", "30113048"));
		item8.setPubDate("Sat, 18 Jan 2020 06:15:00 GMT");
		Item item9 = new Item();
		item9.setTitle("Ognomy MVP Backend API Challenge");
		item9.setDescription("<strong>CHALLENGE OBJECTIVES</strong>\n" + 
				"<ul>\n" + 
				"	<li>Build the backend API for the Ognomy MVP project.</li>\n" + 
				"</ul>\n" + 
				" <br />\n" + 
				"<strong>PROJECT BACKGROUND</strong>\n" + 
				"\n" + 
				"<ul>\n" + 
				"	<li>We are building an MVP of a mobile app that allows patients to schedule and participate in teleconsultations on their mobile device, and of a web app that allows doctors to view teleconsult schedules and participate in teleconsults from a desktop device.</li>\n" + 
				"	<li>For the purpose of this challenge, we need to implement the backend that will support the MVP of this mobile app and web app.</li>\n" + 
				"	<li>This MVP is the 1st...</li></ul><br /><div>Platforms: AWS</div><div>Technologies: MongoDB / Node.js / REST</div><div>Prize: 2100 (1400)</div><div>Registration Period: 2020-01-17 14:04 +00:00 - 2020-01-24 14:07 +00:00</div><div>Open for registration: No </div><div>Submissions Due: 2020-01-24 14:16 +00:00</div><div>Type: Code / develop</div>");
		item9.setGuid(new Guid("false", "30112930"));
		item9.setLink("https://www.topcoder.com/challenge-details/30112930/?type=develop");
		item9.setPubDate("Fri, 17 Jan 2020 14:04:00 GMT");
		List<Item> items = new ArrayList<>();
		items.add(item9);
		items.add(item8);
		items.add(item7);
		items.add(item6);
		items.add(item5);
		items.add(item4);
		items.add(item3);
		items.add(item2);
		items.add(item);
		return items;
	}
	
	public static Channel channel() {
		Channel channel = new Channel();
		channel.setTitle("topcoder - Challenges");
		channel.setDescription("RSS feed service");
		channel.setLink("http://www.topcoder.com");
		channel.setGenerator("RSS for Node");
		channel.setLastBuildDate("Sat, 25 Jan 2020 08:28:49 GMT");
		channel.setAtomLink(null);
		channel.setItems(RSSFeedTestData.items());
		return channel;
	}
	
	public static Feed feed() {
		return new Feed(RSSFeedTestData.channel());
	}
}
