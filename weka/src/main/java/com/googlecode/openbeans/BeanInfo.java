package com.googlecode.openbeans;

import android.media.Image;

//import java.awt.Image;

public interface BeanInfo {

  public static final int ICON_COLOR_16x16 = 1;

  public static final int ICON_COLOR_32x32 = 2;

  public static final int ICON_MONO_16x16 = 3;

  public static final int ICON_MONO_32x32 = 4;

  public PropertyDescriptor[] getPropertyDescriptors();

  public MethodDescriptor[] getMethodDescriptors();

  public EventSetDescriptor[] getEventSetDescriptors();

  public BeanInfo[] getAdditionalBeanInfo();

  public BeanDescriptor getBeanDescriptor();

  public Image getIcon(int iconKind);

  public int getDefaultPropertyIndex();

  public int getDefaultEventIndex();
}