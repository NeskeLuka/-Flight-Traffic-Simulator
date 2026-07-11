package inputFormInterface;

import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

public class FormField {
	private final Label label;
	private final TextField field;

	public FormField(String labelText) {
		this.label = new Label(labelText);
		this.field = new TextField();
	}

	public void addTo(Panel panel) {
		panel.add(label);
		panel.add(field);
	}

	public String getValue() {
		return field.getText().trim();
	}

	public TextField getField() {
		return field;
	}
}