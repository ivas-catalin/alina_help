import java.applet.Applet;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class SampleDistApplet extends Applet{
  DistCanvas dc;
  SampleCanvas sc;
  StatCanvas sc1, sc2;
  Choice vc, samplesize, reps, stat1, stat2;
  Button sample, reset;
  Checkbox anim;
  double min, max;
  Color[] statcolors = {Color.green.darker(),Color.red,Color.pink,Color.blue};
  StatPanel sp, sp2, sp3, sp4;
    double enteredp = Double.NaN;

  public void init(){
    String lab = "";
    setFont(new Font("SansSerif",Font.PLAIN,14));
    min = Double.valueOf(getParameter("min")).doubleValue();
    max = Double.valueOf(getParameter("max")).doubleValue();
    setBackground(Color.white);
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(2,2,2,2);
    c.weighty = 1;
    c.anchor = GridBagConstraints.NORTHWEST;
    setLayout(gridbag);
    String[] stats = {"Mean","Median","Std. Dev."};
    Color[] colors = {Color.green.darker(),Color.red,Color.blue};
    sp = new StatPanel(stats,colors);
    c.gridwidth = 1;
    c.weightx = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.CENTER;
    c.ipadx = 1;
    c.ipady = 1;
    gridbag.setConstraints(sp,c);
    add(sp);
    Panel p1 = new Panel();
    p1.setLayout(new BorderLayout());
    dc = new DistCanvas(min,max,"population characteristic");
    dc.minheight = 125;
    c.gridwidth = 1;
    c.weightx = 10;
    c.fill = GridBagConstraints.BOTH;
    gridbag.setConstraints(dc,c);
    add(dc);
    vc = new Choice();
    vc.addItem("Uniform");
    vc.addItem("Bell shaped");
    vc.addItem("Skewed");
    vc.addItem("Con. Custom");
    vc.addItem("Binary: p = 0.1");
    vc.addItem("Binary: p = 0.2");
    vc.addItem("Binary: p = 0.3");
    vc.addItem("Binary: p = 0.4");
    vc.addItem("Binary: p = 0.5");
    vc.addItem("Binary: p = 0.6");
    vc.addItem("Binary: p = 0.7");
    vc.addItem("Binary: p = 0.8");
    vc.addItem("Binary: p = 0.9");
    vc.addItem("Binary Custom");
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weightx = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.CENTER;
    Panel rp = new Panel();
    rp.setLayout(new GridLayout(0,1));
    rp.setBackground(new Color(230,230,230));
    rp.add(vc);
    reset = new Button("Reset");
    rp.add(reset);
    gridbag.setConstraints(rp,c);
    add(rp);
    dc.addStatPanel(sp);
    sp2 = new StatPanel(stats,colors);
    c.gridwidth = 1;
    c.weightx = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.CENTER;
    gridbag.setConstraints(sp2,c);
    add(sp2);
    sc = new SampleCanvas(min,max,lab);
    sc.minheight = 125;
    sc.addStatPanel(sp2);
    sc.addDistCanvas(dc);
    c.gridwidth = 1;
    c.weightx = 10;
    c.fill = GridBagConstraints.BOTH;
    gridbag.setConstraints(sc,c);
    add(sc);
    sample  = new Button("Sample");
    Panel rp2 = new Panel();
    rp2.setLayout(new GridLayout(0,1));
    rp2.setBackground(new Color(230,230,230));
    rp2.add(sample);
    Panel ssp = new Panel();
    ssp.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
    samplesize = new Choice();
    samplesize.addItem("2");
    samplesize.addItem("3");
    samplesize.addItem("5");
    samplesize.addItem("10");
    samplesize.addItem("30");
    samplesize.addItem("50");
    samplesize.addItem("100");
    samplesize.addItem("1000");
    samplesize.addItem("4000");
    ssp.add(new Label("n =",Label.LEFT));
    ssp.add(samplesize);
    rp2.add(ssp);
    Panel bignp = new Panel();
    bignp.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
    reps = new Choice();
    reps.addItem("1");
    reps.addItem("10");
    reps.addItem("100");
    reps.addItem("1000");
    reps.addItem("10000");
    bignp.add(new Label("N =",Label.LEFT));
    bignp.add(reps);
    rp2.add(bignp);
    anim = new Checkbox("Animate",true);
    /*
    rp2.add(anim);
    */
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weightx = 0;
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.HORIZONTAL;
    gridbag.setConstraints(rp2,c);
    add(rp2);
    Color[] ncolors = {Color.black, Color.green.darker(),Color.red,Color.blue};
    String[] nstats = {"N","Mean","Median","Std. Dev."};
    sp3 = new StatPanel(nstats,ncolors);
    c.gridwidth = 1;
    c.weightx = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.CENTER;
    gridbag.setConstraints(sp3,c);
    add(sp3);
    sc1 = new StatCanvas("Mean",min,max,lab);
		sc1.minheight = 125;
    sc1.addStatPanel(sp3);
    sc.setPrimaryStatCanvas(sc1);
    c.gridwidth = 1;
    c.weightx = 10;
    c.fill = GridBagConstraints.BOTH;
    gridbag.setConstraints(sc1,c);
    add(sc1);
    Panel rp3 = new Panel();
    rp3.setBackground(new Color(230,230,230));
    stat1 = new Choice();
    stat1.addItem("Mean");
    stat1.addItem("Median");
    stat1.addItem("Variance");
    stat1.addItem("Std. Dev.");
    stat1.select(0);
    sc1.setGraphColor(statcolors[stat1.getSelectedIndex()]);
    rp3.add(stat1);
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weightx = 0;
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.HORIZONTAL;
    gridbag.setConstraints(rp3,c);
    add(rp3);
    sp4 = new StatPanel(nstats,ncolors);
    c.gridwidth = 1;
    c.weightx = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.CENTER;
    gridbag.setConstraints(sp4,c);
    add(sp4);
    sc2 = new StatCanvas("Median",min,max,lab);
		sc2.minheight = 125;
    sc2.addStatPanel(sp4);
    sc.setSecondaryStatCanvas(sc2);
    c.gridwidth = 1;
    c.weightx = 10;
    c.fill = GridBagConstraints.BOTH;
    gridbag.setConstraints(sc2,c);
    add(sc2);
    Panel rp4 = new Panel();
    rp4.setBackground(new Color(230,230,230));
    stat2 = new Choice();
    stat2.addItem("Mean");
    stat2.addItem("Median");
    stat2.addItem("Variance");
    stat2.addItem("Std. Dev.");
    stat2.select(1);
    sc2.setGraphColor(statcolors[stat2.getSelectedIndex()]);
    rp4.add(stat2);
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weightx = 0;
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.HORIZONTAL;
    gridbag.setConstraints(rp4,c);
    add(rp4);
  }

  public void resetGraphs(boolean incld){
    if(vc.getSelectedIndex() <= 3){
	if(incld){
	    dc.setXAxis(min,max,"");
	    dc.setShowStats(true);
	}
	sc.setXAxis(min,max,"");
	sc.setShowStats(true);
	if(stat1.getSelectedIndex() == 2)
	    sc1.setXAxis(0.0d,(max-min)*(max-min)/4.0d,"");
	else
	    sc1.setXAxis(min,max,"");
	if(stat2.getSelectedIndex() == 2)
	    sc2.setXAxis(0.0d,(max-min)*(max-min)/4.0d,"");
	else
	    sc2.setXAxis(min,max,"");
    }else{
	double[] xdata = {0.0d,1.0d};
	if(incld){
	    dc.xaxis = new Axis(-.5d,1.5d,xdata,"");
	    dc.points = xdata;
	    dc.halfstep = 0.25d;
	    dc.setShowStats(false);
	    dc.setShowMean(true);
	}
	sc.xaxis = new Axis(-.5d,1.5d,xdata,"");
	sc.points = xdata;
	sc.halfstep = 0.25d;
	sc.setShowStats(false);
	sc.setShowMean(true); 
	sc1.halfstep = 0.25d;     
	double[] cdata = {0.0d,Double.valueOf(samplesize.getSelectedItem()).doubleValue()};
	sc1.xaxis = new Axis(-sc1.halfstep,Double.valueOf(samplesize.getSelectedItem()).doubleValue()+sc1.halfstep,cdata,"");
      double[] cpoints = new double[Integer.valueOf(samplesize.getSelectedItem()).intValue()+1];
      for(int i = 0; i < cpoints.length; i++)
	  cpoints[i] = (double)i;
      sc1.points = cpoints;
      sc1.heights = new double[cpoints.length];
      sc2.halfstep = 0.25d/Double.valueOf(samplesize.getSelectedItem()).doubleValue();
      double[] pdata = {0.0d,1.0d};
      sc2.xaxis = new Axis(-sc2.halfstep,1.0d+sc2.halfstep,pdata,"");
      double[] ppoints = new double[Integer.valueOf(samplesize.getSelectedItem()).intValue()+1];
      for(int i = 0; i < ppoints.length; i++)
	  ppoints[i] = (double)(i*1.0d)/Double.valueOf(samplesize.getSelectedItem()).doubleValue();
      sc2.points = ppoints;
      sc2.heights = new double[ppoints.length];
      //      sc2.setXAxis(0.0d,1.0d,"");
    }
    if(incld){
	if(vc.getSelectedIndex() <= 12)
	   dc.setYAxis(vc.getSelectedIndex());
	else if(!Double.isNaN(enteredp))
	    dc.setCustomYAxis(enteredp);
	else
	    dc.setCustomYAxis(0.5d);
    }
    sc.setYAxis(6.0d);
    sc.reset();
    sc.drawSamples(0,0,false,true);
    sc1.setStat(stat1.getSelectedItem());
    sc1.setGraphColor(statcolors[stat1.getSelectedIndex()]);
    sc1.reset();
    sc1.setYAxis(6.0d);
    sc1.Draw();
    sc1.repaint();
    sc2.setStat(stat2.getSelectedItem());
    sc2.setGraphColor(statcolors[stat2.getSelectedIndex()]);
    sc2.reset();
    sc2.setYAxis(6.0d);
    sc2.Draw();
    sc2.repaint();
  }

    public boolean handleEvent(Event event) {
	if(event.id == Event.ACTION_EVENT){
	    if(event.target.equals(vc) || event.target.equals(samplesize) || event.target.equals(stat1) || event.target.equals(stat2) || event.target.equals(reset)){
		sc.stop();
		updateSamplePanel();
		if(event.target.equals(vc) || event.target.equals(reset)){
		    if(event.target.equals(vc))
			updateDistPanel();
		    if(vc.getSelectedIndex() <= 3){
			stat1.removeAll();
			stat1.addItem("Mean");
			stat1.addItem("Median");
			stat1.addItem("Variance");
			stat1.addItem("Std. Dev.");
			stat1.select(0);
			stat2.removeAll();
			stat2.addItem("Mean");
			stat2.addItem("Median");
			stat2.addItem("Variance");
			stat2.addItem("Std. Dev.");
			stat2.select(1);
		    }else{
			stat1.removeAll();
			stat1.addItem("Count");
			stat1.select(0);
			stat2.removeAll();
			stat2.addItem("Proportion");
			stat2.select(0);
		    }
		    validate();
		}
		boolean incldist = false;
		if(event.target.equals(vc)){
		    incldist = true;
		    if(vc.getSelectedIndex() == 13){
			String s = (String)JOptionPane.showInputDialog(this,"Enter value for p below:","",JOptionPane.PLAIN_MESSAGE,null,null,"");
			enteredp = Double.NaN;
			if ((s != null) && (s.length() > 0)) {
			    try{
				enteredp = Double.valueOf(s).doubleValue();
			    }catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this,"The value for p must be between 0 and 1.");
				incldist = false;
			    }
			}
		    }
		}
		resetGraphs(incldist);
	    }else if(event.target.equals(sample)){
				int bign = new Integer(reps.getSelectedItem()).intValue();
				sc.setYAxis(6.0d);
				boolean animate = false;
				int ssize = new Integer(samplesize.getSelectedItem()).intValue();
				if(bign == 1 && ssize <= 50)
					animate = true;
				sc.drawSamples(ssize,bign,animate,animate);
      }else if(event.target.equals(anim)){
					if(!anim.getState()){
					  sc.stop();
					}
      }
    }
    return super.handleEvent(event);
  }

  void updateStatPanels(){
		updateSamplePanel();
		updateDistPanel();
  }
  
  void updateSamplePanel(){
  	if(vc.getSelectedIndex() <= 3){
      String[] stats = {"Mean","Median","Std. Dev."};
      Color[] colors = {Color.green.darker(),Color.red,Color.blue};
      sp2.setPanel(stats,colors);
    }else{
      String[] stats = {"Prop. of 1s"};
      Color[] colors = {Color.green.darker()};
      sp2.setPanel(stats,colors);
    }
  }
  
  void updateDistPanel(){
  	if(vc.getSelectedIndex() <= 3){
      String[] stats = {"Mean","Median","Std. Dev."};
      Color[] colors = {Color.green.darker(),Color.red,Color.blue};
      sp.setPanel(stats,colors);
    }else{
      String[] stats = {"Prop. of 1s"};
      Color[] colors = {Color.green.darker()};
      sp.setPanel(stats,colors);
    }
  }

}
