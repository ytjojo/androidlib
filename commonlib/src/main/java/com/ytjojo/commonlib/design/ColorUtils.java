/*
 * Copyright 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ytjojo.commonlib.design;


import android.graphics.Color;

public class ColorUtils {

    public static int darken(final int color, float fraction) {
        return blendColors(Color.BLACK, color, fraction);
    }

    public static int lighten(final int color, float fraction) {
        return blendColors(Color.WHITE, color, fraction);
    }

    /**
     * @return luma value according to to YIQ color space.
     */
    public static final int calculateYiqLuma(int color) {
        return Math.round((
                299 * Color.red(color) +
                587 * Color.green(color) +
                114 * Color.blue(color))
        / 1000f);
    }
    
    /**
     * Make a dark color to press effect
     * @return
     */
    protected int makePressColor(int alpha ,int backgroundColor) {
        int r = (backgroundColor >> 16) & 0xFF;
        int g = (backgroundColor >> 8) & 0xFF;
        int b = (backgroundColor >> 0) & 0xFF;
        r = (r - 30 < 0) ? 0 : r - 30;
        g = (g - 30 < 0) ? 0 : g - 30;
        b = (b - 30 < 0) ? 0 : b - 30;
        return Color.argb(alpha, r, g, b);
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
     *              0.0 will return {@code color2}.
     */
    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRatio = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRatio);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRatio);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRatio);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static final int changeBrightness(final int color, float fraction) {
        return calculateYiqLuma(color) >= 128
                ? darken(color, fraction)
                : lighten(color, fraction);
    }

    public static int modifyAlpha(int color, int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }
    public static final class HSV {
		public double h, s, v;
		
		public HSV() {
		}
		
		public HSV(double h, double s, double v) {
			this.h = h;
			this.s = s;
			this.v = v;
		}
		
		public void fromRGB(int rgb) {
			final double r = (double)((rgb >>> 16) & 0xff) / 255.0,
			g = (double)((rgb >>> 8) & 0xff) / 255.0,
			b = (double)(rgb & 0xff) / 255.0,
			max = Math.max(Math.max(r, g), b),
			min = Math.min(Math.min(r, g), b);
			h = 0;
			s = ((max == 0) ? 0 : ((max - min) / max));
			v = max;
			if (max != min) {
				if (max == r) {
					if (g >= b)
						h = (60.0 * ((g - b) / (max - min))) / 360.0;
					else
						h = ((60.0 * ((g - b) / (max - min))) + 360.0) / 360.0;
				} else if (max == g) {
					h = ((60.0 * ((b - r) / (max - min))) + 120.0) / 360.0;
				} else {
					h = ((60.0 * ((r - g) / (max - min))) + 240.0) / 360.0;
				}
			}
		}
		
		public int toRGB(boolean invert) {
			double h = 6.0 * this.h;
			final double v = this.v * 255.0;
			int hi, r, g, b, vi;
			if (h > 5.99) h = 0;
			hi = (int)h % 6;
			h -= (double)hi;
			r = (int)(v * (1.0 - s));
			g = (int)(v * (1.0 - (h * s)));
			b = (int)(v * (1.0 - ((1.0 - h) * s)));
			vi = (int)v;
			switch (hi) {
				case 1:
					b = r;
					r = g;
					g = vi;
					break;
				case 2:
					g = vi;
					break;
				case 3:
					b = vi;
					break;
				case 4:
					g = r;
					r = b;
					b = vi;
					break;
				case 5:
					b = g;
					g = r;
					r = vi;
					break;
				default:
					g = b;
					b = r;
					r = vi;
					break;
			}
			return 0xff000000 | (invert ? (((b >= 255) ? 255 : b) << 16) | (((g >= 255) ? 255 : g) << 8) | ((r >= 255) ? 255 : r) :
					(((r >= 255) ? 255 : r) << 16) | (((g >= 255) ? 255 : g) << 8) | ((b >= 255) ? 255 : b));
		}
	}
	
	public static int blend(int rgb1, int rgb2, float perc1) {
		int r1 = (rgb1 >>> 16) & 0xff;
		final int r2 = (rgb2 >>> 16) & 0xff;
		int g1 = (rgb1 >>> 8) & 0xff;
		final int g2 = (rgb2 >>> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		final int b2 = rgb2 & 0xff;
		final float perc2 = 1.0f - perc1;
		r1 = (int)(((float)r1 * perc1) + ((float)r2 * perc2));
		g1 = (int)(((float)g1 * perc1) + ((float)g2 * perc2));
		b1 = (int)(((float)b1 * perc1) + ((float)b2 * perc2));
		if (r1 > 255)
			r1 = 255;
		else if (r1 < 0)
			r1 = 0;
		if (g1 > 255)
			g1 = 255;
		else if (g1 < 0)
			g1 = 0;
		if (b1 > 255)
			b1 = 255;
		else if (b1 < 0)
			b1 = 0;
		return 0xff000000 | (r1 << 16) | (g1 << 8) | b1;
	}
	
	public static double relativeLuminance(int rgb) {
		//http://www.w3.org/TR/2007/WD-WCAG20-TECHS-20070517/Overview.html#G18
		double RsRGB = (double)((rgb >>> 16) & 0xff) / 255.0,
		GsRGB = (double)((rgb >>> 8) & 0xff) / 255.0,
		BsRGB = (double)(rgb & 0xff) / 255.0,
		R, G, B;
		if (RsRGB <= 0.03928) R = RsRGB / 12.92; else R = Math.pow((RsRGB + 0.055) / 1.055, 2.4);
		if (GsRGB <= 0.03928) G = GsRGB / 12.92; else G = Math.pow((GsRGB + 0.055) / 1.055, 2.4);
		if (BsRGB <= 0.03928) B = BsRGB / 12.92; else B = Math.pow((BsRGB + 0.055) / 1.055, 2.4);
		return (0.2126 * R) + (0.7152 * G) + (0.0722 * B);
	}
	
	public static double contrastRatioL(double luminance1, double luminance2) {
		//http://www.w3.org/TR/2007/WD-WCAG20-TECHS-20070517/Overview.html#G18
		return (luminance1 >= luminance2) ? ((luminance1 + 0.05) / (luminance2 + 0.05)) : ((luminance2 + 0.05) / (luminance1 + 0.05));
	}
	
	public static double contrastRatio(int rgb1, int rgb2) {
		//http://www.w3.org/TR/2007/WD-WCAG20-TECHS-20070517/Overview.html#G18
		return ColorUtils.contrastRatioL(ColorUtils.relativeLuminance(rgb1), ColorUtils.relativeLuminance(rgb2));
	}
	
	public static int parseHexColor(String color) {
		try {
			if (color.charAt(0) == '#')
				color = color.substring(1);
			return 0xff000000 | (0x00ffffff & Integer.parseInt(color, 16));
		} catch (Throwable ex) {
			return 0;
		}
	}
	
	public static String toHexColor(int rgb) {
		final String s = "00000" + Integer.toHexString(rgb);
		return "#" + s.substring(s.length() - 6);
	}
	public static boolean isColorLight(int hex) {
        // convert hex string to int
        int rgb = Integer.parseInt(String.format("%d", hex), 16);

        int red = Color.red(hex);
        int green = Color.green(hex);
        int blue = Color.blue(hex);

        float[] hsb = new float[3];
        Color.colorToHSV(hex, hsb);
//        float[] hsb = Color.RGBtoHSB(red, green, blue, null);

        float brightness = hsb[2];

        return hsb[2] > 0.5;
    }

	/**
	 * 颜色加深处理
	 *
	 * @param RGBValues
	 *            RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
	 *            Android中我们一般使用它的16进制，
	 *            例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
	 *            red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
	 *            所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
	 * @return
	 */
	public static int colorBurn(int RGBValues) {
		int alpha = RGBValues >> 24;
		int red = RGBValues >> 16 & 0xFF;
		int green = RGBValues >> 8 & 0xFF;
		int blue = RGBValues & 0xFF;
		red = (int) Math.floor(red * (1 - 0.1));
		green = (int) Math.floor(green * (1 - 0.1));
		blue = (int) Math.floor(blue * (1 - 0.1));
		return Color.rgb(red, green, blue);
	}
}
