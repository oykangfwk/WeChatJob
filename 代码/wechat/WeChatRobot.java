package com.wechat;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class WeChatRobot {

	private Robot bot = null;
	private Clipboard clip = null;

	public WeChatRobot() {
		try {
			this.clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			this.bot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void OpenWeChat() {
		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_ALT);
		bot.keyPress(KeyEvent.VK_W);

		bot.keyRelease(KeyEvent.VK_CONTROL);
		bot.keyRelease(KeyEvent.VK_ALT);

		bot.delay(1000);
	}

	public void ChooseFriends(String name) {
		Transferable text = new StringSelection(name);
		clip.setContents(text, null);
		bot.delay(1000);
		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_F);
		bot.keyRelease(KeyEvent.VK_CONTROL);

		bot.delay(1000);

		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_V);
		bot.keyRelease(KeyEvent.VK_CONTROL);

		bot.delay(2000);

		bot.keyPress(KeyEvent.VK_ENTER);

	}

	public void SendMessage(String message) {
		Transferable text = new StringSelection(message);
		clip.setContents(text, null);
		bot.delay(1000);
		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_V);
		bot.keyRelease(KeyEvent.VK_CONTROL);
		bot.delay(1000);

		bot.keyPress(KeyEvent.VK_ENTER);

		bot.delay(1000);
		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_ALT);
		bot.keyPress(KeyEvent.VK_W);

		bot.keyRelease(KeyEvent.VK_CONTROL);
		bot.keyRelease(KeyEvent.VK_ALT);
	}
	
	public void SendMessageImage(Image message) throws Exception {
		//Transferable text = new StringSelection(message);
		//clip.setContents(text, null);
		setClipboardImage(message);
		getImageFromClipboard();
		bot.delay(1000);
		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_V);
		bot.keyRelease(KeyEvent.VK_CONTROL);
		bot.delay(1000);

		bot.keyPress(KeyEvent.VK_ENTER);

		bot.delay(1000);
		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_ALT);
		bot.keyPress(KeyEvent.VK_W);

		bot.keyRelease(KeyEvent.VK_CONTROL);
		bot.keyRelease(KeyEvent.VK_ALT);
		//Ω· ¯º”1
		System.out.println("SendMessageImage--IntegerAction.getCnt()="+IntegerAction.getCnt());
		IntegerAction.setCnt(IntegerAction.getCnt()+1);;
	}

	/**
	 * Õ˘ºÙ«–∞Â–¥Õº∆¨
	 * 
	 * @param image
	 */
	protected void setClipboardImage(final Image image) {
		Transferable trans = new Transferable() {
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}

			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
					return image;
				throw new UnsupportedFlavorException(flavor);
			}

		};
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
	}

	/**
	 * ¥”ºÙ«–∞Â∂¡»°ÕºœÒ
	 * @return
	 * @throws Exception
	 */
	public Image getImageFromClipboard() throws Exception {
		Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable cc = sysc.getContents(null);
		if (cc == null)
			return null;
		else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
			return (Image) cc.getTransferData(DataFlavor.imageFlavor);
		return null;
	}
}
