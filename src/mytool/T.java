package mytool;

import java.awt.GridBagConstraints;

public class T {
	public static GridBagConstraints gbc(int...args) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		for (int i = 0; i < args.length; i++) {
			switch (i) {
			case 0:
				gbc.gridx = args[i];
				break;
			case 1:
				gbc.gridy = args[i];
				break;
			case 2:
				gbc.gridwidth = args[i];
				break;
			case 3:
				gbc.gridheight = args[i];
				break;
			case 4:
				gbc.weightx = args[i];
				break;
			case 5:
				gbc.weighty = args[i];
				break;
			case 6:
				break;
			default:
				System.out.println("no this arg to set");
				break;
			}
		}
		gbc.fill = GridBagConstraints.BOTH;
		return gbc;
	}

	public static void gbcSet() {

	}

}
