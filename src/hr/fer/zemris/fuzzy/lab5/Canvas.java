package hr.fer.zemris.fuzzy.lab5;

import java.util.List;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private List<Point> points = new ArrayList<>();

	public Canvas() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	public void mouseEntered(MouseEvent mouseEvent) {
	}

	public void mousePressed(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	public void mouseReleased(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	public void mouseMoved(MouseEvent mouseEvent) {
	}

	public void mouseDragged(MouseEvent mouseEvent) {
		points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
	}

	public void mouseExited(MouseEvent mouseEvent) {
	}

	public void reset() {
		points = new ArrayList<>();
		repaint();
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		int i = 0;
		if (!points.isEmpty()) {
			Point a = points.get(i);
			i++;
			while (i < points.size()) {
				Point b = points.get(i);
				graphics.drawLine((int) Math.round(a.getX()), (int) Math.round(a.getY()), (int) Math.round(b.getX()),
						(int) Math.round(b.getY()));
				a = b;
				i++;
			}
		}
		repaint();
	}
}