package net.okdi.api.dao;

import java.util.Map;

import net.okdi.api.entity.VoiceSet;

public interface VoiceSetMapper {

	public VoiceSet queryVoiceSet(Long memberId);

	public void insertVoiceSet(Map<String, Object> map);

	public void updateVoiceSet(Map<String, Object> map);
 
}