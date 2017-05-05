package com.fortech.model.dto;

import java.util.List;

public class VinylInventoryListDto {
	List<VinylInventoryDto> vinyls;

	public VinylInventoryListDto() {

	}

	public VinylInventoryListDto(List<VinylInventoryDto> vinyls) {
		this.vinyls = vinyls;
	}

	public List<VinylInventoryDto> getVinyls() {
		return this.vinyls;
	}

	public void setVinyls(List<VinylInventoryDto> vinyls) {
		this.vinyls = vinyls;
	}
}
