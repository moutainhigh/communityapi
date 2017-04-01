package net.okdi.apiV2.dao;

import java.util.List;
import java.util.Map;

import net.okdi.apiV2.entity.NewUserRegisterActivity;

public interface NewUserRegisterActivityMapperV2 {

	public List<Map<String, Object>> queryNewUserExperienceGold();

	public String updateNewUserExperienceGold(NewUserRegisterActivity userRegisterActivity);

	
	
}
