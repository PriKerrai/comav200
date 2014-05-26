package com.coma.client.widgets;

import java.util.List;

import com.coma.client.ProposalAvgVote;
import com.coma.client.ProposalAvgVotesData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.chart.series.Series.LabelPosition;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class ShowVotesOnProposalDialog {

	private static List<ProposalAvgVote> proposalAvarageVoteList;

	public static List<ProposalAvgVote> getProposalAvgVotesList() {
		return proposalAvarageVoteList;
	}

	public static void setProposalAvgVotesList(List<ProposalAvgVote> proposalAvgVotesList) {

		ShowVotesOnProposalDialog.proposalAvarageVoteList = proposalAvgVotesList;
	}

	public interface DataPropertyAccess extends PropertyAccess<ProposalAvgVote> {
		ValueProvider<ProposalAvgVote, Double> avgVote();

		ValueProvider<ProposalAvgVote, String> name();

		@Path("id")
		ModelKeyProvider<ProposalAvgVote> nameKey();
	}

	private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);
	private FramedPanel panel;
	private Dialog dialog;

	public Dialog showVotesOnProposalDialog() {

		dialog = new Dialog();
		dialog.setHeadingText("Save as new group model");
		dialog.setWidget(new HTML("Are you sure this is the diagram\n you want to accept as the new group model?\n"));
		dialog.setWidth(700);
		dialog.setHeight(600);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

		final ListStore<ProposalAvgVote> store = new ListStore<ProposalAvgVote>(dataAccess.nameKey());
		store.addAll(ProposalAvgVotesData.getData());

		final Chart<ProposalAvgVote> chart = new Chart<ProposalAvgVote>();
		chart.setStore(store);
		chart.setShadowChart(false);

		NumericAxis<ProposalAvgVote> axis = new NumericAxis<ProposalAvgVote>();
		axis.setPosition(Position.BOTTOM);
		axis.addField(dataAccess.avgVote());
		TextSprite title = new TextSprite("Avarage Votes");
		title.setFontSize(18);
		axis.setTitleConfig(title);
		axis.setDisplayGrid(true);
		axis.setMinimum(0);
		axis.setMaximum(10);
		chart.addAxis(axis);

		CategoryAxis<ProposalAvgVote, String> catAxis = new CategoryAxis<ProposalAvgVote, String>();
		catAxis.setPosition(Position.LEFT);
		catAxis.setField(dataAccess.name());
		title = new TextSprite("Model Creator");
		title.setFontSize(18);
		catAxis.setTitleConfig(title);
		chart.addAxis(catAxis);

		final BarSeries<ProposalAvgVote> bar = new BarSeries<ProposalAvgVote>();
		bar.setYAxisPosition(Position.BOTTOM);
		bar.addYField(dataAccess.avgVote());
		bar.addColor(RGB.GREEN);
		SeriesLabelConfig<ProposalAvgVote> config = new SeriesLabelConfig<ProposalAvgVote>();
		config.setLabelPosition(LabelPosition.OUTSIDE);
		bar.setLabelConfig(config);
		chart.addSeries(bar);

		final RGB[] colors = {
				new RGB(255, 0, 0), new RGB(255, 128, 0), new RGB(255, 255, 0), new RGB(128, 255, 0), new RGB(0, 255, 0)};

		bar.setRenderer(new SeriesRenderer<ProposalAvgVote>() {
			@Override
			public void spriteRenderer(Sprite sprite, int index, ListStore<ProposalAvgVote> store) {
				double value = dataAccess.avgVote().getValue(store.get(index));
				if(value < 3) {
					sprite.setFill(colors[(int) 0]);
				}
				else if(value < 5) {
					sprite.setFill(colors[(int) 1]);
				}
				else if(value < 7) {
					sprite.setFill(colors[(int) 2]);
				}
				else if(value < 9) {
					sprite.setFill(colors[(int) 3]);
				}
				else if(value < 11) {
					sprite.setFill(colors[(int) 4]);
				}
				sprite.redraw();
			}
		});

		panel = new FramedPanel();
		panel.setLayoutData(new MarginData(10));
		panel.setCollapsible(true);
		panel.setHeadingText("Bar Renderer Chart");
		panel.setPixelSize(620, 500);
		panel.setBodyBorder(true);

		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		panel.add(layout);

		chart.setLayoutData(new VerticalLayoutData(1, 1));
		layout.add(chart);
		dialog.setWidget(layout);
		return dialog;
	}

}



