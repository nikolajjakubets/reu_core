package gr.reunion.javaBuffer.buffItem.dynamicHtmls;

import javolution.text.TextBuilder;
import javolution.util.FastList;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import gr.reunion.datatables.ReunionTable;
import gr.reunion.javaBuffer.BuffInstance;
import gr.reunion.javaBuffer.BufferMenuCategories;
import gr.reunion.javaBuffer.BufferPacketCategories;
import gr.reunion.javaBuffer.BufferPacketSender;
import gr.reunion.javaBuffer.PlayerMethods;

/**
 * @author -=GodFather=-
 */
public class GenerateHtmls
{
	/* Constants for dynamic htmls */
	private static final String _headHtml = "<html><title>AIO Item Buffer</title><body><center>";
	private static final String _endHtml = "</center></body></html>";
	
	/**
	 * Shows the available profiles to edit
	 * @param player
	 * @param action
	 */
	public static void showSchemeToEdit(L2PcInstance player, String action)
	{
		FastList<String> profileNames = PlayerMethods.getProfiles(player);
		
		TextBuilder tb = new TextBuilder();
		tb.append(_headHtml);
		tb.append("Choose the profile<br></center><font color=00FFFF>Scheme Profiles:</font><center><img src=\"L2UI.SquareGray\" width=280 height=1><table bgcolor=131210>");
		for (String profile : profileNames)
		{
			tb.append("<tr>");
			tb.append("<td align=center><button value=\"" + profile + "\" action=\"bypass -h Aiobuff_" + action + "_" + profile + "\" width=135 height=28 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></td>");
			tb.append("</tr>");
		}
		tb.append("</table><img src=\"L2UI.SquareGray\" width=280 height=1>");
		tb.append(_endHtml);
		
		BufferPacketSender.sendPacket(player, tb.toString(), BufferPacketCategories.DYNAMIC, 0);
	}
	
	/**
	 * Shows the available buffs to add to a profile
	 * @param player
	 * @param profile
	 * @param category
	 * @param bypass
	 */
	public static void showBuffsToAdd(L2PcInstance player, String profile, BufferMenuCategories category, String bypass)
	{
		FastList<Integer> ownedBuffs = PlayerMethods.getProfileBuffs(profile, player);
		int i = 0;
		
		TextBuilder tb = new TextBuilder();
		tb.append(_headHtml);
		tb.append("Choose the buffs to be added<br></center>");
		switch (category)
		{
			case CHANT:
				tb.append("<font color=00FFFF>Scheme Buffer: Chant</font>");
				break;
			case DANCE:
				tb.append("<font color=00FFFF>Scheme Buffer: Dance</font>");
				break;
			case SONG:
				tb.append("<font color=00FFFF>Scheme Buffer: Song</font>");
				break;
			case OVERLORD:
				tb.append("<font color=00FFFF>Scheme Buffer: Overlord</font>");
				break;
			case PROPHET:
				tb.append("<font color=00FFFF>Scheme Buffer: Prophet</font>");
				break;
			case ELDER:
				tb.append("<font color=00FFFF>Scheme Buffer: Elder</font>");
				break;
			case DWARF:
				tb.append("<font color=00FFFF>Scheme Buffer: Dwarf</font>");
				break;
			case MISC:
				tb.append("<font color=00FFFF>Scheme Buffer: Misc</font>");
				break;
			default:
				break;
		}
		tb.append("<center><img src=\"L2UI.SquareGray\" width=280 height=1>");
		for (BuffInstance buffInst : ReunionTable.getInstance().getBuffs().values())
		{
			// Just a check to know if this buff
			// Is in the category we wish to be
			if (buffInst.getCategory() != category)
			{
				continue;
			}
			
			int id = buffInst.getId();
			Integer level = buffInst.getLevel();
			String description = buffInst.getDescription();
			String name = buffInst.getName();
			
			// Check if the buff id exists in the owned buffs fastlist
			// Remember the l2pc has only the buffId contained
			if ((ownedBuffs != null) && ownedBuffs.contains(id))
			{
				continue;
			}
			
			if ((i % 2) == 0)
			{
				tb.append("<table bgcolor=131210>");
			}
			else
			{
				tb.append("<table>");
			}
			tb.append("<tr>");
			if ((id == 4699) || (id == 4700))
			{
				tb.append("<td width=40><button action=\"bypass -h Aiobuff_" + bypass + "_" + profile + "_" + id + "\" width=32 height=32 back=\"icon.skill1331\" fore=\"icon.skill1331\"></td>");
			}
			if ((id == 4702) || (id == 4703))
			{
				tb.append("<td width=40><button action=\"bypass -h Aiobuff_" + bypass + "_" + profile + "_" + id + "\" width=32 height=32 back=\"icon.skill1332\" fore=\"icon.skill1332\"></td>");
			}
			if (id < 1000)
			{
				tb.append("<td width=40><button action=\"bypass -h Aiobuff_" + bypass + "_" + profile + "_" + id + "\" width=32 height=32 back=\"icon.skill0" + id + "\" fore=\"icon.skill0" + id + "\"></td>");
			}
			if ((id > 1000) && (id != 4699) && (id != 4700) && (id != 4702) && (id != 4703))
			{
				tb.append("<td width=40><button action=\"bypass -h Aiobuff_" + bypass + "_" + profile + "_" + id + "\" width=32 height=32 back=\"icon.skill" + id + "\" fore=\"icon.skill" + id + "\"></td>");
			}
			
			tb.append("<td><table>");
			tb.append("<tr><td width=220>" + name + "<font color=a1a1a1> Lv</font> <font color=ae9977>" + level + "</font></td></tr>");
			tb.append("<tr><td width=220><font color=b0bccc>" + description + "</font></td></tr></table></td></tr>");
			tb.append("</table>");
			i++;
		}
		tb.append("<br><br><img src=L2UI.SquareWhite width=280 height=1><button value=\"Back\" action=\"bypass -h Aiobuff_Chat_scheme.htm\" width=90 height=21 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\">");
		tb.append(_endHtml);
		
		BufferPacketSender.sendPacket(player, tb.toString(), BufferPacketCategories.DYNAMIC, 0);
	}
	
	/**
	 * Shows the available buffs to delete from a profile
	 * @param player
	 * @param profile
	 * @param bypass
	 */
	public static void showBuffsToDelete(L2PcInstance player, String profile, String bypass)
	{
		FastList<Integer> ownedBuffs = PlayerMethods.getProfileBuffs(profile, player);
		// int i = 0;
		
		if (ownedBuffs == null)
		{
			player.sendMessage("There are no buffs in that profile.");
			return;
		}
		
		TextBuilder tb = new TextBuilder();
		tb.append(_headHtml);
		tb.append("Choose the buffs to be deleted<br></center>");
		tb.append("<font color=00FFFF>Scheme Buffer: Remove</font>");
		// Temporary fix for long html
		tb.append("<center><img src=\"L2UI.SquareGray\" width=280 height=1></center><table width=280>");
		// tb.append("<center><img src=\"L2UI.SquareGray\" width=280 height=1>");
		for (BuffInstance buffInst : ReunionTable.getInstance().getBuffs().values())
		{
			int id = buffInst.getId();
			// Integer level = buffInst.getLevel();
			// String description = buffInst.getDescription();
			String name = buffInst.getName();
			
			// Check if the buff id exists in the owned buffs fastlist
			// Remember the l2pc has only the buffId contained
			if (!ownedBuffs.contains(id))
			{
				continue;
			}
			
			// Temporary fix for long html
			tb.append("<tr>");
			tb.append("<td><a action=\"bypass -h Aiobuff_" + bypass + "_" + profile + "_" + id + "\">" + name + "</a></td>");
			tb.append("</tr>");
		}
		
		// Temporary fix for long html
		tb.append("</table>");
		tb.append("<center>");
		tb.append("<br><br><img src=L2UI.SquareWhite width=280 height=1><button value=\"Back\" action=\"bypass -h Aiobuff_Chat_main.htm\" width=90 height=21 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\">");
		tb.append(_endHtml);
		
		BufferPacketSender.sendPacket(player, tb.toString(), BufferPacketCategories.DYNAMIC, 0);
	}
}