import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import sun.audio.*;
import java.awt.event.*;
import java.util.TreeSet;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.PriorityQueue;

public class ChampionSelectGUI extends JFrame implements ActionListener, WindowListener {
	private ArrayList<JButton> champions;
	private JPanel panel;
	private JScrollPane scroll;
	private JLayeredPane bottomPanel;
	private ImageIcon blueBanSlot;
	private ImageIcon purpleBanSlot;
	private ImageIcon chatSlot;
	private JLabel leftTeam;
	private ImageIcon leftTeamIcon;
	private JLayeredPane leftPanel;
	private JLabel rightTeam;
	private ImageIcon rightTeamIcon;
	private JLayeredPane rightPanel;
	private JPanel centerPanel;
	private JLayeredPane tabs;
	private JTextField search;
	private boolean blueBan = false;
	private boolean purpleBan = false;
	private boolean bluePick = false;
	private boolean purplePick = false;
	private boolean finishedPicking = false;
	private JButton quit;
	private JButton noban;
	private JLabel blueBanHighlight;
	private JLabel purpleBanHighlight;
	private JLabel bluePickHighlight;
	private JLabel purplePickHighlight;
	private int numBlueBans = 0;
	private int numPurpleBans = 0;
	private int numPicks = 0;
	private int numBluePicks = 0;
	private int numPurplePicks = 0;
	private final String[] summNameArr = new String[1];
	private final String[] regionArr = new String[1];
	private final String[] roleArr = new String[1];
	private final Boolean[] teamArr = new Boolean[1];

	public ArrayList<String> bannedChamps = new ArrayList<String>();
	private ArrayList<String> blueBans = new ArrayList<String>();
	private ArrayList<String> purpleBans = new ArrayList<String>();
	public ArrayList<String> bluePicks = new ArrayList<String>();
	public ArrayList<String> purplePicks = new ArrayList<String>();
	private static final int MINIMUM_USER_GAMES = 15;
	private static final int MINIMUM_GLOBAL_GAMES = 40;
	private static final int X_RECOMMENDAITONS = 5;
	private static final ArrayList<String> CHAMPIONLIST = new ArrayList<String>();
	static {
		try {
			File champListFile = new File(".data/championlists/allchamps.txt");
			Scanner input = new Scanner(champListFile);
			while (input.hasNextLine()) {
				CHAMPIONLIST.add(input.nextLine());
			}
	 	} catch (Exception e) {
	 		System.out.println(e);
	 	}
	}

	public void setupPanel() {
		panel = new JPanel();
		panel.setLayout(new ModifiedFlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.BLACK);
		panel.setOpaque(true);
	}

	public void setupScroll() {
		tabs = new JLayeredPane();
		scroll = new JScrollPane(panel);
		champions = new ArrayList<JButton>();
		addChampions(panel, CHAMPIONLIST);
		tabs = new JLayeredPane();
		ImageIcon tabIcon = new ImageIcon(getClass().getResource("background/ChampionsTab.png"));
		tabs.setPreferredSize(new Dimension(724, 53));
		JLabel championTab = new JLabel();
		championTab.setIcon(tabIcon);
		tabs.add(championTab, 50);
		championTab.setBounds(0, 0, tabIcon.getIconWidth(), tabIcon.getIconHeight());
		search = new JTextField(10);
		search.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {search.setText("");}
			public void focusLost(FocusEvent e) {}
		});
		tabs.add(search, 1000);
		search.setBounds(600, 10, 100, 25);
	}

	public void setupCenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(tabs);
		centerPanel.add(scroll);
		add(centerPanel, BorderLayout.CENTER);
	}

	public void setupBottomPanel() {
		chatSlot = new ImageIcon(getClass().getResource("background/BottomCenter.png"));
		blueBanSlot = new ImageIcon(getClass().getResource("background/LeftTeamBans.png"));
		purpleBanSlot = new ImageIcon(getClass().getResource("background/RightTeamBans.png"));
		bottomPanel = new JLayeredPane();
		bottomPanel.setPreferredSize(new Dimension(1280, 243));
		JLabel blueBanLabel = new JLabel();
		JLabel chatLabel = new JLabel();
		JLabel purpleBanLabel = new JLabel();
		blueBanLabel.setIcon(blueBanSlot);
		chatLabel.setIcon(chatSlot);
		purpleBanLabel.setIcon(purpleBanSlot);
		bottomPanel.add(blueBanLabel, new Integer(0));
		bottomPanel.add(chatLabel, new Integer(0));
		bottomPanel.add(purpleBanLabel, new Integer(0));
		quit = new JButton();
		quit.setName("quit");
		quit.setOpaque(false);
		quit.addActionListener(this);
		quit.setBorderPainted(false);
		quit.setContentAreaFilled(false);
		quit.setBounds(27, 165, 240, 30);
		bottomPanel.add(quit, new Integer(1));
		noban = new JButton("Click here if the current team missed a ban");
		noban.setName("noban");
		noban.setBackground(Color.GRAY);
		noban.setForeground(Color.WHITE);
		noban.setBorderPainted(false);
		noban.addActionListener(this);
		noban.setBounds(400, 50, 400, 100);
		bottomPanel.add(noban, new Integer(1));
		add(bottomPanel, BorderLayout.PAGE_END);
		blueBanLabel.setBounds(0, 0, 279, 243);
		chatLabel.setBounds(279, 0, 1004, 243);
		purpleBanLabel.setBounds(1004, 0, 1280, 243);
	}

	public void setupSidePanels() {
		leftTeamIcon = new ImageIcon(getClass().getResource("background/LeftTeam.png"));
		leftTeam = new JLabel(leftTeamIcon);
		rightTeamIcon = new ImageIcon(getClass().getResource("background/RightTeam.png"));
		rightTeam = new JLabel(rightTeamIcon);
		leftTeam.setPreferredSize(new Dimension(273, 501));
		rightTeam.setPreferredSize(new Dimension(273, 501));
		scroll.setPreferredSize(new Dimension(724, 484));
		leftPanel = new JLayeredPane();
		leftPanel.setPreferredSize(new Dimension(273, 501));
		leftTeam.setBounds(0, 0, 273, 501);
		leftPanel.add(leftTeam, new Integer(0));
		add(leftPanel, BorderLayout.LINE_START);
		rightPanel = new JLayeredPane();
		rightPanel.setPreferredSize(new Dimension(273, 501));
		rightTeam.setBounds(0, 0, 273, 501);
		rightPanel.add(rightTeam, new Integer(0));
		add(rightPanel, BorderLayout.LINE_END);
	}

	public ChampionSelectGUI() {
		setupPanel();
		setupScroll();
		setupCenterPanel();
		setupBottomPanel();
		setupSidePanels();
		addWindowListener(this);
	}

	public static JDialog createSummonerNameDialog(final ChampionSelectGUI t) {
		final JPanel contentPanel = new JPanel();
		JLabel request = new JLabel("Please enter your summoner name");
		contentPanel.add(request);
		request.setAlignmentX(Component.CENTER_ALIGNMENT);
		final JTextField input = new JTextField(10);
		input.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(input);
		JButton ok = new JButton("ok");
		contentPanel.add(ok);
		ok.setSize(200, 150);
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		final JDialog d = new JDialog(t, "Summoner Name", true);
		d.setContentPane(contentPanel);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.setSize(400, 100);
		d.setLocationRelativeTo(t);
		d.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		d.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				int ret = JOptionPane.showConfirmDialog(contentPanel, "Are you sure you want to exit?", "Choose an option", JOptionPane.YES_NO_OPTION);
				if (ret == 0) {
					System.exit(0);
				}
			}
			public void windowOpened(WindowEvent e) {
				input.requestFocus();
			}

			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowActivated(WindowEvent e) { }
			public void windowDeactivated(WindowEvent e) { }

		});
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (input.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPanel, "Please input a summoner name.", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					t.summNameArr[0] = input.getText();
					d.setVisible(false);
				}
			}
		});
		return d;
	}

	public static JDialog createRegionDialog(final ChampionSelectGUI t) {
		final JPanel contentPanel = new JPanel();
		JLabel request = new JLabel("Please select your region");
		contentPanel.add(request);
		request.setAlignmentX(Component.CENTER_ALIGNMENT);
		final Choice input = new Choice();
		input.add("---Select Region---");
		input.add("NA");
		input.add("EUW");
		input.add("KR");
		input.add("EUNE");
		input.add("BR");
		input.add("RU");
		input.add("TR");
		input.add("OCE");
		input.add("LAN");
		input.add("LAS");
		contentPanel.add(input);
		JButton ok = new JButton("ok");
		contentPanel.add(ok);
		ok.setSize(200, 150);
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		final JDialog d = new JDialog(t, "Region", true);
		d.setContentPane(contentPanel);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.setSize(400, 100);
		d.setLocationRelativeTo(t);
		d.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		d.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				int ret = JOptionPane.showConfirmDialog(contentPanel, "Are you sure you want to exit?", "Choose an option", JOptionPane.YES_NO_OPTION);
				if (ret == 0) {
					System.exit(0);
				}
			}
			public void windowOpened(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowActivated(WindowEvent e) { }
			public void windowDeactivated(WindowEvent e) { }

		});
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (input.getSelectedItem().equals("---Select Region---")) {
					JOptionPane.showMessageDialog(contentPanel, "Please select a region.", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					t.regionArr[0] = input.getSelectedItem();
					d.setVisible(false);
				}
			}
		});
		return d;
	}

	public static JDialog createRoleDialog(final ChampionSelectGUI t) {
		final JPanel contentPanel = new JPanel();
		JLabel request = new JLabel("Please select your role");
		contentPanel.add(request);
		request.setAlignmentX(Component.CENTER_ALIGNMENT);
		final Choice input = new Choice();
		input.add("---Select Role---");
		input.add("Top");
		input.add("Jungle");
		input.add("Mid");
		input.add("ADC");
		input.add("Support");
		contentPanel.add(input);
		JButton ok = new JButton("ok");
		contentPanel.add(ok);
		ok.setSize(200, 150);
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		final JDialog d = new JDialog(t, "Role", true);
		d.setContentPane(contentPanel);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.setSize(400, 100);
		d.setLocationRelativeTo(t);
		d.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		d.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				int ret = JOptionPane.showConfirmDialog(contentPanel, "Are you sure you want to exit?", "Choose an option", JOptionPane.YES_NO_OPTION);
				if (ret == 0) {
					System.exit(0);
				}
			}
			public void windowOpened(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowActivated(WindowEvent e) { }
			public void windowDeactivated(WindowEvent e) { }

		});
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (input.getSelectedItem().equals("---Select Region---")) {
					JOptionPane.showMessageDialog(contentPanel, "Please select a region.", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					t.roleArr[0] = input.getSelectedItem();
					d.setVisible(false);
				}
			}
		});
		return d;
	}

	public static JDialog createTeamDialog(final ChampionSelectGUI t) {
		final JPanel contentPanel = new JPanel();
		JLabel request = new JLabel("Please select your team");
		contentPanel.add(request);
		request.setAlignmentX(Component.CENTER_ALIGNMENT);
		final Choice input = new Choice();
		input.add("---Select Team---");
		input.add("Blue");
		input.add("Red");
		contentPanel.add(input);
		JButton ok = new JButton("ok");
		contentPanel.add(ok);
		ok.setSize(200, 150);
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		final JDialog d = new JDialog(t, "Team", true);
		d.setContentPane(contentPanel);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.setSize(400, 100);
		d.setLocationRelativeTo(t);
		d.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		d.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				int ret = JOptionPane.showConfirmDialog(contentPanel, "Are you sure you want to exit?", "Choose an option", JOptionPane.YES_NO_OPTION);
				if (ret == 0) {
					System.exit(0);
				}
			}
			public void windowOpened(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowActivated(WindowEvent e) { }
			public void windowDeactivated(WindowEvent e) { }

		});
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (input.getSelectedItem().equals("---Select Team---")) {
					JOptionPane.showMessageDialog(contentPanel, "Please select a Team.", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					if (input.getSelectedItem().equals("Blue")) {
						t.teamArr[0] = true;
					} else {
						t.teamArr[0] = false;
					}
					d.setVisible(false);
				}
			}
		});
		return d;
	}

	public static String stripName(String n) {
		String result = "";
		String[] parts = n.split(" ");
		for (String s : parts) {
			result += s;
		}
		result = result.toLowerCase();
		return result;
	}

	public static String combineName(String n) {
		String result = "";
		String[] parts = n.split(" ");
		for (String s : parts) {
			result += s;
		}
		return result;
	}

	public static void main(String[] args) {
		final ChampionSelectGUI t = new ChampionSelectGUI();
		t.setSize(1280, 780);
		t.setTitle("Champion Select GUI v 0.1");
		t.setLocationRelativeTo(null);
		JDialog sND = createSummonerNameDialog(t);
		sND.setVisible(true);
		JDialog rD = createRegionDialog(t);
		rD.setVisible(true);
		JDialog roleD = createRoleDialog(t);
		roleD.setVisible(true);
		JDialog teamD = createTeamDialog(t);
		teamD.setVisible(true);
		String summonerName = t.summNameArr[0];
		summonerName = stripName(summonerName);
		String region = t.regionArr[0];
		String role = t.roleArr[0];
		boolean blueSide = t.teamArr[0];
		HashMap<String, Double> userData = Helpers.loadUserData(summonerName, region);		
		HashMap<String, Double> globalData = Helpers.loadGlobalData(region);
		if (userData == null) {
	    	int ret = JOptionPane.showConfirmDialog(t, "Unable to load data for user. Would you like to download now?", "Choose an option", JOptionPane.YES_NO_OPTION);
			if (ret == 0) {
				userData = ChampStatisticReader.collectUserStatistics(summonerName, region, MINIMUM_USER_GAMES);
			} else {
				JOptionPane.showMessageDialog(t, "Cannot continue without data. Exiting", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		if (globalData == null) {
			int ret = JOptionPane.showConfirmDialog(t, "Unable to load data for region. Would you like to download now?", "Choose an option", JOptionPane.YES_NO_OPTION);
			if (ret == 0) {
				globalData = ChampStatisticReader.collectGlobalStatistics(region);
			} else {
				JOptionPane.showMessageDialog(t, "Cannot continue without data. Exiting", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		t.setVisible(true);
	    t.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    t.getChampionBans(true);
	    t.getChampionPicks(true);
	    double maxScore = .5;
	    String maxChamp = "";
	    PriorityQueue<ScoredChampion> pq = new PriorityQueue<ScoredChampion>(X_RECOMMENDAITONS, new ScoredChampionComparator());
	    ArrayList<String> roleChamps = new ArrayList<String>();
	    try {
		    Scanner input = new Scanner(new File(".data/roles/" + role + "champions.txt"));
		    while (input.hasNextLine()) {
		    	roleChamps.add(input.nextLine().split("\n")[0]);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    ArrayList<String> allyList = null;
	    ArrayList<String> enemyList = null;
	    if (blueSide) {
	    	allyList = t.bluePicks;
	    	enemyList = t.purplePicks;
	    } else  {
	    	allyList = t.purplePicks;
	    	enemyList = t.bluePicks;
	    }

	    HashMap<String, History> allyHistMap = Helpers.loadHistMap(true);
	    HashMap<String, History> enemyHistMap = Helpers.loadHistMap(false);
	    if (allyHistMap == null || enemyHistMap == null) {
	    	JOptionPane.showMessageDialog(t, "Unable to load data. Exiting", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    for (String champion : roleChamps) {
	    	if (t.bannedChamps.contains(champion) || t.bluePicks.contains(champion) || t.purplePicks.contains(champion)) {
	    		continue;
	    	}
    		    ArrayList<History> allyHists = new ArrayList<History>();
			    ArrayList<History> enemyHists = new ArrayList<History>();
	    	for (String ally : allyList) {
	    		String allyKey = Helpers.combineAlphabetical(champion, combineName(ally));
	    		History allyHist = allyHistMap.get(allyKey);
	    		if (allyHist != null && ((allyHist.wins + allyHist.losses) > MINIMUM_GLOBAL_GAMES)) allyHists.add(allyHist);
	    	}
	    	for (String enemy : enemyList) {
	    		String enemyKey = champion + "," + combineName(enemy);
	    		History enemyHist = enemyHistMap.get(enemyKey);
	    		if (enemyHist != null && ((enemyHist.wins + enemyHist.losses) > MINIMUM_GLOBAL_GAMES)) {
		    		enemyHists.add(enemyHist);
				}
	    	}
	    	if (globalData.get(champion) == null) {
	    		System.out.println(champion);
	    	}
	    	double score = Calculations.calculateWinMultiplier(allyHists, enemyHists, globalData.get(champion));
	    	if (userData.get(champion) != null) {
	    		score = score * userData.get(champion)/globalData.get(champion);
	    	}
	    	ScoredChampion currSC = new ScoredChampion(champion,score);
	    	if (pq.size() < X_RECOMMENDAITONS) {
	    		pq.add(currSC);
	    	} else {
	    		if (pq.peek().compareTo(currSC) < 0) {
	    			pq.poll();
	    			pq.add(currSC);
	    		}
	    	}
	    	if (score > maxScore) {
	    		maxScore = score;
	    		maxChamp = champion;
	    	}
	    }
	    if (maxChamp.equals("")) {
	    	JOptionPane.showMessageDialog(t, "Unable to recommend chamion based on data. Exiting", "Error", JOptionPane.ERROR_MESSAGE);
	    	System.exit(0);
	    }
	    JOptionPane.showMessageDialog(t, pq, "Recommendations", JOptionPane.INFORMATION_MESSAGE);
	    System.exit(0);
	}

	public void getChampionBans(boolean blueSide) {
		blueBanHighlight = new JLabel() {
    		protected void paintComponent(Graphics g) {
	    	    g.setColor(getBackground());
    	    	g.fillRect(0, 0, getWidth(), getHeight());
       			super.paintComponent(g);
		    }
		};
		blueBanHighlight.setBackground(new Color(255, 217, 0, 120));
		bottomPanel.add(blueBanHighlight, new Integer(1));
		blueBanHighlight.setBounds(23, 29, 245, 135);

		purpleBanHighlight = new JLabel() {
    		protected void paintComponent(Graphics g) {
	    	    g.setColor(getBackground());
    	    	g.fillRect(0, 0, getWidth(), getHeight());
       			super.paintComponent(g);
		    }
		};
		purpleBanHighlight.setBackground(new Color(255, 217, 0, 120));
		bottomPanel.add(purpleBanHighlight, new Integer(1));
		purpleBanHighlight.setBounds(1014, 29, 245, 168);
		purpleBanHighlight.setVisible(false);

		blueBan = true;
		ArrayList<String> temp = CHAMPIONLIST;
	    while (numBlueBans < 3 || numPurpleBans < 3) {
	    	try {
	    		ArrayList<String> aL = findChampions();
	    		if (!aL.equals(temp)) {
	    			addChampions(panel, aL);
	    			temp = aL;
	    		}
	    		Thread.sleep(100);
	    	} catch (InterruptedException e) {
	    		continue;
	    	}
	    }
	}

	public void getChampionPicks(boolean blueSide) {
		bluePickHighlight = new JLabel() {
    		protected void paintComponent(Graphics g) {
	    	    g.setColor(getBackground());
    	    	g.fillRect(0, 0, getWidth(), getHeight());
       			super.paintComponent(g);
		    }
		};
		bluePickHighlight.setBackground(new Color(218, 116, 32, 120));
		leftPanel.add(bluePickHighlight, new Integer(1));
		bluePickHighlight.setBounds(18, 10, 210, 88);

		purplePickHighlight = new JLabel() {
    		protected void paintComponent(Graphics g) {
	    	    g.setColor(getBackground());
    	    	g.fillRect(0, 0, getWidth(), getHeight());
       			super.paintComponent(g);
		    }
		};
		purplePickHighlight.setBackground(new Color(218, 116, 32, 120));
		rightPanel.add(purplePickHighlight, new Integer(1));
		purplePickHighlight.setBounds(45, 10, 210, 88);
		purplePickHighlight.setVisible(false);

		JButton finished = new JButton("Click here when finished inputting selected champions");
		finished.setName("finished");
		finished.setBackground(Color.GRAY);
		finished.setForeground(Color.WHITE);
		finished.setBorderPainted(false);
		finished.addActionListener(this);
		finished.setBounds(400, 50, 400, 100);
		bottomPanel.add(finished, new Integer(4));
		ArrayList<String> temp = CHAMPIONLIST;
	    while (!finishedPicking) {
	    	try {
	    		ArrayList<String> aL = findChampions();
	    		if (!aL.equals(temp)) {
	    			addChampions(panel, aL);
	    			temp = aL;
	    		}
	    		Thread.sleep(100);
	    	} catch (InterruptedException e) {
	    		continue;
	    	}
	    }
	}

	private int banBlueChampion(String cN) {
		if (bannedChamps.contains(cN)) {
			return -1;
		}
		if (!cN.equals("noban")) {
			ImageIcon champIcon = new ImageIcon(getClass().getResource("championicons/32px/" + cN + "Square.png"));
			JLabel champLabel = new JLabel();
			JLabel champName = new JLabel(cN);
			champName.setBackground(Color.BLACK);
			champName.setForeground(Color.WHITE);
			champLabel.setIcon(champIcon);
			bottomPanel.add(champLabel, new Integer(3));
			int i = blueBans.size();
			champLabel.setBounds(29, 32 + 36*i, 32, 32);
			bottomPanel.add(champName, new Integer(3));
			champName.setBounds(64, 24 + 36*i, 150, 50);
			blueBans.add(cN);
			bannedChamps.add(cN);
		}
		numBlueBans += 1;
		return 0;
	}

	private int banPurpleChampion(String cN) {
		if (bannedChamps.contains(cN)) {
			return -1;
		}
		if (!cN.equals("noban")) {
			ImageIcon champIcon = new ImageIcon(getClass().getResource("championicons/32px/" + cN + "Square.png"));
			JLabel champLabel = new JLabel();
			champLabel.setIcon(champIcon);
			bottomPanel.add(champLabel, new Integer(3));
			int i = purpleBans.size();
			champLabel.setBounds(1200, 32 + 36*i, 32, 32);
			JLabel champName = new JLabel(cN, SwingConstants.RIGHT);
			champName.setBackground(Color.BLACK);
			champName.setForeground(Color.WHITE);
			bottomPanel.add(champName, new Integer(3));
			champName.setBounds(1045, 24 + 36*i, 150, 50);
			purpleBans.add(cN);
			bannedChamps.add(cN);
		}
		numPurpleBans += 1;
		return 0;
	}

	private int pickBlueChampion(String cN) {
		if (bannedChamps.contains(cN) || bluePicks.contains(cN) || purplePicks.contains(cN)) {
			return -1;
		}
		ImageIcon champIcon = new ImageIcon(getClass().getResource("championicons/" + cN + "Square.png"));
		JLabel champLabel = new JLabel();
		champLabel.setIcon(champIcon);
		leftPanel.add(champLabel, new Integer(2));
		int i = numBluePicks;
		champLabel.setBounds(91, 22 + 99*i, 64, 64);
		bluePicks.add(cN);
		numBluePicks += 1;
		return 0;
	}

	private int pickPurpleChampion(String cN) {
		if (bannedChamps.contains(cN) || bluePicks.contains(cN) || purplePicks.contains(cN)) {
			return -1;
		}
		ImageIcon champIcon = new ImageIcon(getClass().getResource("championicons/" + cN + "Square.png"));
		JLabel champLabel = new JLabel();
		champLabel.setIcon(champIcon);
		rightPanel.add(champLabel, new Integer(2));
		int i = numPurplePicks;
		champLabel.setBounds(118, 22 + 99*i, 64, 64);
		purplePicks.add(cN);
		numPurplePicks += 1;
		return 0;
	}

	private void addChampions(JPanel p, ArrayList<String> aL) {
		p.removeAll();
		for (String c : aL) {
			ImageIcon icon = new ImageIcon(getClass().getResource("championicons/" + c + "Square.png"));
			JButton currB = new JButton(icon);
			currB.addActionListener(this);
			currB.setPreferredSize(new Dimension(64, 64));
			p.add(currB);
		}
		p.revalidate();
		repaint();
	}

	private ArrayList<String> findChampions() {
		String searchContents = search.getText();
		ArrayList<String> result = new ArrayList<String>();
		for (String c : CHAMPIONLIST) {
			if (c.toLowerCase().contains(searchContents.toLowerCase())) {
				result.add(c);
			}
		}
		return result;
	}

	public void actionPerformed(ActionEvent evt) {
		String text = ((JButton) evt.getSource()).getName();
		if (text != null && text.equals("quit")) {
			System.exit(0);
		}
		if (text != null && text.equals("finished")) {
			finishedPicking = true;
			bluePickHighlight.setVisible(false);
			purplePickHighlight.setVisible(false);
			bluePick = false;
			purplePick = false;
			return;
		}
		String output = "";
		if (text != null && text.equals("noban")) {
			output = "noban";
		} else {
			String[] arr = ((JButton) evt.getSource()).getIcon().toString().split("/");
			output = arr[arr.length-1].split("Square")[0];
			output = output.replaceAll("%20", " ");
		}
		if (blueBan) {
			int err = banBlueChampion(output);
			if (err == 0) {
				purpleBan = true;
				blueBan = false;
				blueBanHighlight.setVisible(false);
				purpleBanHighlight.setVisible(true);
			}
			return;
		}
		if (purpleBan) {
			int err = banPurpleChampion(output);
			if (err == 0) {
				purpleBan = false;
				purpleBanHighlight.setVisible(false);
				if (numPurpleBans < 3) {
					blueBan = true;
					blueBanHighlight.setVisible(true);
				} else {
					bottomPanel.remove(blueBanHighlight);
					bottomPanel.remove(purpleBanHighlight);
					bluePick = true;
				}
			}
			return;
		}
		if (bluePick) {
			int err = pickBlueChampion(output);
			if (err == 0) {
				if (numBluePicks == 1 || numBluePicks == 3) {
					bluePick = false;
					bluePickHighlight.setVisible(false);
					purplePickHighlight.setBounds(45, 10 + numPurplePicks*100, 210, 88);
					purplePickHighlight.setVisible(true);
					purplePick = true;
				}
				else if (numBluePicks == 5) {
					finishedPicking = true;
					bluePickHighlight.setVisible(false);
					bluePick = false;
				}
				else {
					bluePickHighlight.setBounds(18, 10 + numBluePicks*100, 210, 88);
				}
			}
			return;
		}
		if (purplePick) {
			int err = pickPurpleChampion(output);
			if (err == 0) {
				if (numPurplePicks == 2 || numPurplePicks == 4) {
					bluePick = true;
					bluePickHighlight.setBounds(18, 10 + numBluePicks*100, 210, 88);
					bluePickHighlight.setVisible(true);
					purplePick = false;
					purplePickHighlight.setVisible(false);
				} else {
					purplePickHighlight.setBounds(45, 10 + numPurplePicks*100, 210, 88);
				}
			}
			return;
		}
	}
	
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) {
		search.requestFocus();
	}

	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}

	public void windowIconified(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
}