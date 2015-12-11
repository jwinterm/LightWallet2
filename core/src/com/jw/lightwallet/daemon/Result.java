package com.jw.lightwallet.daemon;

public class Result {
	
	BlockHeader block_header;
	String status;
	
	
	public BlockHeader getBlock_header() {
		return block_header;
	}
	public void setBlock_header(BlockHeader block_header) {
		this.block_header = block_header;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
