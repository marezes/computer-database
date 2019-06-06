package mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.excilys.cdb.dto.DTOCompany;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.ModelCompany;

public class MapperCompanyTest {

	@Test
	public void testModelCompanyListToDTOCompanyList() {
		ArrayList<ModelCompany> modelCompanyList = new ArrayList<ModelCompany>();
		modelCompanyList.add(new ModelCompany.ModelCompanyBuilder()
				.withId(1)
				.withName("Bidon")
				.build());
		ArrayList<DTOCompany> dtoCompanyList = new MapperCompany().modelCompanyListToDTOCompanyList(modelCompanyList);
		
		assertEquals("1", String.valueOf(dtoCompanyList.get(0).getId()));
		assertEquals("Bidon", dtoCompanyList.get(0).getName());
	}

}
