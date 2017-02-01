package spellchecker.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.Highlighter.HighlightPainter;

import spellchecker.Spellchecker;
import spellchecker.content.SpellCheckItem;

public class SpellcheckerUI extends JFrame implements DocumentListener {
	private final static Color HIGHLIHT_COLOR = Color.RED;
	private final static Color CURRENT_HIGHLIHT_COLOR = Color.GRAY;

	private Spellchecker spellchecker;
	private java.util.List<SpellCheckItem> flaggedItems = new java.util.ArrayList();

	private JScrollPane contentPane;
	private JTextArea contentTextArea;
	private JLabel notInDictionaryLabel;
	private JTextField notInDictionaryTextField;
	private JPanel notInDictionaryPanel;
	private JPanel controlsPanel;

	public SpellcheckerUI() {
		super("A simple Spellchecker demo");
		initComponents();

		contentTextArea.getDocument().addDocumentListener(this);
		contentTextArea.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent event) {
				spellItemSelected(event);
			}
		});

		spellchecker = new Spellchecker();
		loadContent();

	}

	private void initComponents() {

		contentTextArea = new JTextArea();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		contentTextArea.setColumns(20);
		contentTextArea.setLineWrap(true);
		contentTextArea.setRows(5);
		contentTextArea.setWrapStyleWord(true);
		contentTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane = new JScrollPane(contentTextArea);
		GridLayout layout = new GridLayout(3, 1); // 3 rows, 1 column
		getContentPane().setLayout(layout);
		add(contentPane);

		notInDictionaryLabel = new JLabel("Not in Dictionary:");
		notInDictionaryTextField = new JTextField(20);
		notInDictionaryPanel = new JPanel();
		notInDictionaryPanel.add(notInDictionaryLabel);
		notInDictionaryPanel.add(notInDictionaryTextField);
		notInDictionaryPanel.setLayout(new FlowLayout());
		add(notInDictionaryPanel);

		controlsPanel = new JPanel();
		controlsPanel.setLayout(new FlowLayout());

		JButton ignoreButton = new JButton("Ignore");
		ignoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// move highlite to next
			}
		});

		JButton addButton = new JButton("Add to Dictionary");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// source javax.swing.JButton
				String notInDictionaryText = notInDictionaryTextField.getText();
				if (notInDictionaryText != null) {
					spellchecker.addToDictionary(notInDictionaryText);
					spellchecker.saveDictionary();
				}
			}
		});

		JButton saveContentButton = new JButton("Save Content");
		saveContentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spellchecker.saveContent(contentTextArea.getText());
			}
		});

		controlsPanel.add(ignoreButton);
		controlsPanel.add(addButton);
		controlsPanel.add(saveContentButton);
		add(controlsPanel);

		pack();

	}

	public void changedUpdate(DocumentEvent ev) {
		// System.out.println("insertUpdate"+ ev.getLength());
		//
		// if (ev.getLength() != 1) {
		// return;
		// }
		// int pos = ev.getOffset();
	}

	public void removeUpdate(DocumentEvent ev) {
		// TODO
	}

	@Override
	public void insertUpdate(DocumentEvent ev) {
		// TODO
	}

	public void spellItemSelected(CaretEvent event) {
		// int dot = event.getDot();
		int mark = event.getMark();

		SpellCheckItem item = spellchecker.findSpellCheckItemByOffset(mark);
		if (item != null && item.isFlagged()) {
			notInDictionaryTextField.setText(item.getToken());
		} else {
			notInDictionaryTextField.setText("");
		}

	}

	void loadContent() {
		String text = spellchecker.getContentAsString();
		contentTextArea.setText(text);

		spellchecker.performSpellcheck();

		for (SpellCheckItem item : spellchecker.getContent()) {
			if (item.isFlagged()) {
				flaggedItems.add(item);
				HighlightSpellCheckItem(item, HIGHLIHT_COLOR);
			}
		}
	}

	void HighlightSpellCheckItem(SpellCheckItem item, Color color) {
		Highlighter highlighter = contentTextArea.getHighlighter();
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(color);

		int p0 = item.getOffset();
		int p1 = p0 + item.getToken().length();
		try {
			highlighter.addHighlight(p0, p1, painter);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(() -> {
			SpellcheckerUI ex = new SpellcheckerUI();
			ex.setVisible(true);
		});
	}

}