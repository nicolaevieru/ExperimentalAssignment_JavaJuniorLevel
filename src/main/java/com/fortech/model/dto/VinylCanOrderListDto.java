package com.fortech.model.dto;

import java.util.List;

public class VinylCanOrderListDto {

	private List<VinylCanOrderDto> vinyls;

	public VinylCanOrderListDto() {

	}

	public VinylCanOrderListDto(List<VinylCanOrderDto> vinyls) {
		this.vinyls = vinyls;
	}

	public List<VinylCanOrderDto> getVinyls() {
		return vinyls;
	}

	public void setVinyls(List<VinylCanOrderDto> vinyls) {
		this.vinyls = vinyls;
	}

}
