package gateconsole.modules.admin;

import gate.annotation.Description;
import gate.annotation.Emoji;
import gate.annotation.Name;
import gate.annotation.Public;
import javax.enterprise.context.RequestScoped;

@Public
@Emoji("1F600")
@RequestScoped
@Name("Emojis")
@Description("Emojis do Unicode")
public class EmojiScreen extends gate.base.Screen
{

	public String call()
	{
		return "/views/gateconsole/modules/admin/Emoji/View.html";
	}
}
