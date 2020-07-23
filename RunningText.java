// libraries
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RunningText extends JFrame {
	// komponen untuk tampilan
	private JPanel p1, p2;
	private JButton btnLambat, btnStartStop, btnCepat, btnText, btnColor;
	private JTextField tfText;

	// komponen yang mengatur tulisan
	Timer t;
	Color textColor, color;
	int xCoordinate = 170;
	int yCoordinate = 25;

	// constructor
	public RunningText() {
		setLayout(new GridLayout(3, 1));

		// mengatur panel
		p1 = new JPanel(new FlowLayout());
		p1.setBackground(Color.WHITE);
		add(p1);

		p2 = new JPanel(new FlowLayout());
		p2.setBackground(Color.WHITE);
		add(p2);

		// isi dari p1
		btnLambat = new JButton("Perlambat");
		p1.add(btnLambat);

		btnStartStop = new JButton("Start");
		p1.add(btnStartStop);

		btnCepat = new JButton("Percepat");
		p1.add(btnCepat);

		// isi dari p2
		p2.add(new JLabel("Masukkan Tulisan"));

		tfText = new JTextField("", 15);
		p2.add(tfText);

		btnText = new JButton("Tetapkan Tulisan");
		p2.add(btnText);

		// event listeners
		btnLambat.addActionListener(new ListenerPerlambat());
		btnStartStop.addActionListener(new Listener());
		btnCepat.addActionListener(new ListenerPercepat());

		// event handling
		btnText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p2.removeAll();

				btnColor = new JButton("Ubah Warna");
				p2.add(btnColor);

				btnColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						color = JColorChooser.showDialog(RunningText.this, "Pilihan Warna", textColor);
						if (color != null) {
							textColor = color;
						}

						repaint();
					}
				});

				add(new RunningTextPanel(tfText.getText(), 20));
			}
		});
	}

	public class ListenerPerlambat implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int delay = t.getDelay();

			delay = delay + 5;
			t.setDelay(delay);
		}
	}

	public class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (btnStartStop.getText().equals("Start")) {
				t.start();
				btnStartStop.setText("Stop");
			} else {
				t.stop();
				btnStartStop.setText("Start");
			}
		}
	}

	public class ListenerPercepat implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int delay = t.getDelay();

			delay = delay - 5;
			t.setDelay(delay);
		}
	}

	public class RunningTextPanel extends JPanel {
		// komponen
		String message;

		// constructor
		public RunningTextPanel(String message, int delay) {
			this.message = message;
			t = new Timer(delay, new TimerListener());
		}

		// method
		public void paintComponent(Graphics g) {
			if (xCoordinate < -300) {
				xCoordinate = 400;
			} else {
				xCoordinate -= 5;
			}

			g.setColor(textColor);
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g.drawString(message, xCoordinate, yCoordinate);
		}
	}

	public class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}

	public static void main(String[] args) {
		// membuat objek
		RunningText frame = new RunningText();

		// mengatur GUI
		frame.setTitle("Running Text");
		frame.setSize(450, 150);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}