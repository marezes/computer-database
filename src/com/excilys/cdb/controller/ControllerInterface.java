package com.excilys.cdb.controller;

import com.excilys.cdb.dto.DTO;
import java.util.ArrayList;

public interface ControllerInterface {
	public ArrayList<DTO> process(String... args);
}
