import java.applet.Applet;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class PopSampleApplet extends Applet{
  DistCanvas dc;
  SampleCanvas sc;
  Choice vc, samplesize;
  Button sample;
  Checkbox anim;
  double min, max;
  StatPanel sp, sp2;

  public void init(){
    String lab = "";
    min = Double.valueOf(getParameter("min")).doubleValue();
    max = Double.valueOf(getParameter("max")).doubleValue();
    setBackground(Color.white);
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(2,2,2,2);
    c.anchor = GridBagConstraints.NORTHWEST;
    c.weighty = 1;
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
    rp.setBackground(new Color(230,230,230));
    rp.add(vc);
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
    anim = new Checkbox("Animate",true);
//    rp2.add(anim);
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weightx = 0;
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.HORIZONTAL;
    gridbag.setConstraints(rp2,c);
    add(rp2);
  }

  public boolean handleEvent(Event event) {
    if(event.id == Event.ACTION_EVENT){
	if(event.target.equals(vc)){
	    if(vc.getSelectedIndex() == 13){
		String s = (String)JOptionPane.showInputDialog(this,"Enter value for p below:","",JOptionPane.PLAIN_MESSAGE,null,null,"");
		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
		    try{
			double pd = Double.valueOf(s).doubleValue();
			double[] xdata = {0.0d,1.0d};
			dc.xaxis = new Axis(-.5d,1.5d,xdata,"");
			dc.points = xdata;
			dc.setShowStats(false);
			dc.setShowMean(true);
			dc.halfstep = 0.25d;
			sc.xaxis = new Axis(-.5d,1.5d,xdata,"");
			sc.points = xdata;
			sc.halfstep = 0.25d;
			sc.setShowStats(false);
			sc.setShowMean(true);
			updateStatPanels();
			dc.setCustomYAxis(pd);
			sc.setYAxis(6.0d);
			sc.drawSamples(0,0,false,true);
		    }catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this,"The value for p must be between 0 and 1.");
		    }
		}
	    }else{
		if(vc.getSelectedIndex() <= 3){
		    dc.setXAxis(min,max,"");
		    dc.setShowStats(true);
		    sc.setXAxis(min,max,"");
		    sc.setShowStats(true);
		}else{
		    double[] xdata = {0.0d,1.0d};
		    dc.xaxis = new Axis(-.5d,1.5d,xdata,"");
		    dc.points = xdata;
		    dc.setShowStats(false);
		    dc.setShowMean(true);
		    dc.halfstep = 0.25d;
		    sc.xaxis = new Axis(-.5d,1.5d,xdata,"");
		    sc.points = xdata;
		    sc.halfstep = 0.25d;
		    sc.setShowStats(false);
		    sc.setShowMean(true);
		}
		updateStatPanels();
		dc.setYAxis(vc.getSelectedIndex());
		sc.setYAxis(6.0d);
		sc.drawSamples(0,0,false,true);
	    }
	}else if(event.target.equals(samplesize)){
	    sc.drawSamples(0,0,false,true);
	}else if(event.target.equals(sample)){
	    sc.setYAxis(6.0d);
	    boolean animate = false;
	    int ssize = new Integer(samplesize.getSelectedItem()).intValue();
		if(ssize <= 50)
		    animate = true;
		sc.drawSamples(ssize,1,animate,true);
	}else if(event.target.equals(anim)){
	    if(!anim.getState()){
		sc.stop();
	    }
	}
    }
    return super.handleEvent(event);
  }

  void updateStatPanels(){
    if(vc.getSelectedIndex() <= 3){
      String[] stats = {"Mean","Median","Std. Dev."};
      Color[] colors = {Color.green.darker(),Color.red,Color.blue};
      sp.setPanel(stats,colors);
      sp2.setPanel(stats,colors);
    }else{
      String[] stats = {"Prop. of 1s"};
      Color[] colors = {Color.green.darker()};
      sp.setPanel(stats,colors);
      sp2.setPanel(stats,colors);
    }
  }

}
