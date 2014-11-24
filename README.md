FancyCoverFlow
==============

THIS PROJECT IS NO LONGER MAINTAINED!
=====================================

## What is FancyCoverFlow?
FancyCoverFlow is a flexible Android widget providing out of the box view transformations to give your app a unique look and feel. Curious about what FancyCoverFlow can do for you? Check out the FancyCoverFlow examples on Google Play.

[![Google Play Link](http://davidschreiber.github.io/FancyCoverFlow/en_generic_rgb_wo_45.png)](https://play.google.com/store/apps/details?id=at.technikum.mti.fancycoverflow.samples)
![FancyCoverFlow Framed Screenshot](http://davidschreiber.github.io/FancyCoverFlow/screenshot2.png)

## How to use?
Using FancyCoverFlow in your Android app is as simple as

	fancyCoverFlow = new FancyCoverFlow(context);
	fancyCoverFlow.setMaxRotation(45);
	fancyCoverFlow.setUnselectedAlpha(0.3f);
	fancyCoverFlow.setUnselectedSaturation(0.0f);
	fancyCoverFlow.setUnselectedScale(0.4f);

You can also inflate FancyCoverFlow from XML:

	<at.technikum.mti.fancycoverflow.FancyCoverFlow
	        android:layout_width="match_parent"
        	android:layout_height="match_parent"
	        fcf:maxRotation="45"
	        fcf:unselectedAlpha="0.3"
        	fcf:unselectedSaturation="0.0"
	        fcf:unselectedScale="0.4" />
