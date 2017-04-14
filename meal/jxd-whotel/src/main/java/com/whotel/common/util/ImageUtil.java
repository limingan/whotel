package com.whotel.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * 图像工具
 * 
 */
public class ImageUtil {
	public static final String JPG = "JPEG";
	public static final String GIF = "gif";
	public static final String PNG = "png";
	public static final String BMP = "bmp";
	public static final String UNKNOWN = "unknown";

	private ImageUtil() {}
	
	/**
	 * Get Image type. Caution: will invoke close if o is an InputStream
	 * 
	 * @param input an <code>Object</code> to be used as an input source, such as a <code>File</code>, readable
	 *            <code>RandomAccessFile</code>, or <code>InputStream</code>.
	 * 
	 * @return gif/JPEG/png/bmp or null if format unknown
	 */
	public static String getFormatName(Object o) {
		ImageInputStream iis = null;
		try {
			// Create an image input stream on the image
			iis = ImageIO.createImageInputStream(o);
			// Find all image readers that recognize the image format
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				// No readers found
				return UNKNOWN;
			}
			// Use the first reader
			ImageReader reader = iter.next();
			// Close stream
			iis.close();
			// Return the format name
			return reader.getFormatName();
		} catch (IOException e) {
			// Do nothing
		}
		// The image could not be read
		return null;
	}

	/**
	 * Get image format, imgFileName must be full path name of this image file.
	 * 
	 * @param imgFileName
	 * @return
	 */
	public static String getFormatName(String imgFileName) {
		File f = new File(imgFileName);
		if (!f.exists() || f.isDirectory()) {
			throw new RuntimeException("Image not exists: " + imgFileName);
		}
		if (!f.canRead()) {
			throw new RuntimeException("Image is not readable: " + imgFileName);
		}
		return getFormatName(f);
	}

	/**
	 * TODO 对图片裁剪，并把裁剪完的新图片保存 。
	 */
	public static void clipImage(String srcpath, String despath, int x, int y, int width, int height, boolean center) {

		FileInputStream is = null;
		ImageInputStream iis = null;

		if (StringUtils.isBlank(srcpath)) {
			return;
		}
		String ext = getFormatName(srcpath);
		try {
			// 读取图片文件
			is = new FileInputStream(srcpath);
			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或
			 * "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);
			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
			 * 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);

			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();

			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			if (center) {
				File f = new File(srcpath);
				BufferedImage orgBi = ImageIO.read(f);

				int cx = (orgBi.getWidth() - width) / 2;
				int cy = (orgBi.getHeight() - height) / 2;

				if (cx > 0) {
					x = cx;
				}
				if (cy > 0) {
					y = cy;
				}
			}

			Rectangle rect = new Rectangle(x, y, width, height);

			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);

			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的 BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);

			// 保存新图片
			if (StringUtils.isNotBlank(despath)) {
				ImageIO.write(bi, ext, new File(despath));
			} else {
				ImageIO.write(bi, ext, new File(srcpath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (iis != null)
					iis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 图片水印
	 * 
	 * @param pressImg 水印图片
	 * @param targetImg 目标图片
	 * @param x 修正值 默认在中间
	 * @param y 修正值 默认在中间
	 * @param alpha 透明度
	 */
	public final static void pressImage(String pressImg, String targetImg, Integer x, Integer y, Integer w, Integer h,
			float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			if (w == null || h == null) {
				w = wideth_biao;
				h = height_biao;
			}

			if (x == null || y == null) {
				x = (wideth - w) / 2;
				y = (height - h) / 2;
			}

			g.drawImage(src_biao, x, y, w, h, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write(image, getFormatName(targetImg), img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final static void pressImage(String targetImg, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File("e://watermarker.png"));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) - 10, (height - height_biao) - 10, wideth_biao, height_biao,
					null);
			// 水印文件结束
			g.dispose();
			ImageIO.write(image, getFormatName(targetImg), img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText 水印文字
	 * @param targetImg 目标图片
	 * @param fontName 字体名称
	 * @param fontStyle 字体样式
	 * @param color 字体颜色
	 * @param fontSize 字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度
	 * @throws IOException
	 */
	public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) throws IOException {
		pressText(pressText, targetImg, new Font(fontName, fontStyle, fontSize), color, x, y, alpha);
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText 水印文字
	 * @param targetImg 目标图片
	 * @param fontName 字体名称
	 * @param fontStyle 字体样式
	 * @param color 字体颜色
	 * @param fontSize 字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度
	 * @throws IOException
	 */
	public static void pressText(String pressText, String targetImg, Font font, Color color, Integer x, Integer y,
			float alpha) throws IOException {
		File img = new File(targetImg);
		Image src = ImageIO.read(img);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(font);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawString(pressText, (width - (getLength(pressText) * font.getSize())) / 2 + x, y);
		g.dispose();
		ImageIO.write(image, getFormatName(targetImg), img);

	}

	public static int[] getImageSize(String file) {
		File img = new File(file);
		Image src;
		int[] sizes = { 0, 0 };
		try {
			src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			sizes[0] = width;
			sizes[1] = height;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sizes;
	}

	/**
	 * 缩放
	 * 
	 * @param filePath 图片路径
	 * @param height 高度
	 * @param width 宽度
	 * @param bb 比例不对时是否需要补白
	 */
	public static boolean resize(String filePath, int width, int height, Boolean widthRatio, Boolean bb) {
		try {
			double ratio = 0; // 缩放比例
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = null;
			if (width != 0 && height != 0) {
				itemp = bi.getScaledInstance(bi.getWidth(), bi.getHeight(), Image.SCALE_SMOOTH);
			}
			if (widthRatio != null && ((widthRatio && width == 0) || (!widthRatio && height == 0))) {
				return false;
			}
			if (bi.getHeight() == height && bi.getWidth() == width) {
				return false;
			}
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (widthRatio == null) {
					if (bi.getHeight() > bi.getWidth()) {
						ratio = (new Integer(height)).doubleValue() / bi.getHeight();
					} else {
						ratio = (new Integer(width)).doubleValue() / bi.getWidth();
					}
				} else if (widthRatio) {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				} else {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, getFormatName(filePath), f);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) throws IOException {
		// pressImage("E:\\ladybug.gif", "E:\\123456.jpg", 0, 0, 5f);
		// pressText("我是文字水印", "E:\\ladybug.gif", "黑体", 36, Color.white, 80,
		// 0, 0, 3f);
		// resize("E:\\cate-06-4.jpg", 0, 48, false, false);
		Font f = new Font("黑体", Font.BOLD, 26);

		ImageUtil.pressText("水印测试", "E:\\f.jpg",f,new Color(253, 252, 236), 0, 0, 1f);
		ImageUtil.pressImage("E:\\11.jpg", 0.8f);
	}

	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
}
