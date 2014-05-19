package com.coma.client.widgets;

import java.util.List;

import com.coma.client.ModelInfo;
import com.coma.client.ProposalAvgVotes;
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
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.Resizable.Dir;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent.ExpandHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class ShowVotesOnProposalDialog {
	
	private static List<ProposalAvgVotes> proposalAvarageVoteList;

	public static List<ProposalAvgVotes> getProposalAvgVotesList() {
		return proposalAvarageVoteList;
	}

	public static void setProposalAvgVotesList(List<ProposalAvgVotes> proposalAvgVotesList) {
		
		ShowVotesOnProposalDialog.proposalAvarageVoteList = proposalAvgVotesList;
	}

	public interface DataPropertyAccess extends PropertyAccess<ProposalAvgVotes> {
		ValueProvider<ProposalAvgVotes, Double> data1();

		ValueProvider<ProposalAvgVotes, String> name();

		@Path("id")
		ModelKeyProvider<ProposalAvgVotes> nameKey();
	}

	private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);
	private FramedPanel panel;
	private Dialog dialog;

	public Dialog showVotesOnProposalDialog() {

		dialog = new Dialog();
		dialog.setHeadingText("Save as new group model");
		dialog.setWidget(new HTML("Are you sure this is the diagram\n you want to accept as the new group model?\n"));
		dialog.setPixelSize(700, 600);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		final ListStore<ProposalAvgVotes> store = new ListStore<ProposalAvgVotes>(dataAccess.nameKey());
		store.addAll(ProposalAvgVotes.getData(proposalAvarageVoteList.size(), 0, 10));

		final Chart<ProposalAvgVotes> chart = new Chart<ProposalAvgVotes>();
		chart.setStore(store);
		chart.setShadowChart(false);

		NumericAxis<ProposalAvgVotes> axis = new NumericAxis<ProposalAvgVotes>();
		axis.setPosition(Position.BOTTOM);
		axis.addField(dataAccess.data1());
		TextSprite title = new TextSprite("Avarage Votes");
		title.setFontSize(18);
		axis.setTitleConfig(title);
		axis.setDisplayGrid(true);
		axis.setMinimum(0);
		axis.setMaximum(10);
		chart.addAxis(axis);

		CategoryAxis<ProposalAvgVotes, String> catAxis = new CategoryAxis<ProposalAvgVotes, String>();
		catAxis.setPosition(Position.LEFT);
		catAxis.setField(dataAccess.name());
		title = new TextSprite("Model Creator");
		title.setFontSize(18);
		catAxis.setTitleConfig(title);
		chart.addAxis(catAxis);

		final BarSeries<ProposalAvgVotes> bar = new BarSeries<ProposalAvgVotes>();
		bar.setYAxisPosition(Position.BOTTOM);
		bar.addYField(dataAccess.data1());
		bar.addColor(RGB.GREEN);
		SeriesLabelConfig<ProposalAvgVotes> config = new SeriesLabelConfig<ProposalAvgVotes>();
		config.setLabelPosition(LabelPosition.OUTSIDE);
		bar.setLabelConfig(config);
		chart.addSeries(bar);

		final RGB[] colors = {
				new RGB(255, 0, 0), new RGB(255, 128, 0), new RGB(255, 255, 0), new RGB(128, 255, 0), new RGB(0, 255, 0)};

		bar.setRenderer(new SeriesRenderer<ProposalAvgVotes>() {
			@Override
			public void spriteRenderer(Sprite sprite, int index, ListStore<ProposalAvgVotes> store) {
				double value = dataAccess.data1().getValue(store.get(index));
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

		final Resizable resize = new Resizable(panel, Dir.E, Dir.SE, Dir.S);
		resize.setMinHeight(400);
		resize.setMinWidth(400);

		panel.addExpandHandler(new ExpandHandler() {
			@Override
			public void onExpand(ExpandEvent event) {
				resize.setEnabled(true);
			}
		});
		panel.addCollapseHandler(new CollapseHandler() {
			@Override
			public void onCollapse(CollapseEvent event) {
				resize.setEnabled(false);
			}
		});

		new Draggable(panel, panel.getHeader()).setUseProxy(false);

		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		panel.add(layout);

		chart.setLayoutData(new VerticalLayoutData(1, 1));
		layout.add(chart);


		verticalLayoutContainer.add(new FieldLabel(panel), new VerticalLayoutData(1, -1));
		dialog.setWidget(verticalLayoutContainer);
		return dialog;
	}

}



