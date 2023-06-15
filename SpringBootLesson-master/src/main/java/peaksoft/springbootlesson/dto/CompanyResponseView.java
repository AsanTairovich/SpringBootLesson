package peaksoft.springbootlesson.dto;

import jdk.dynalink.linker.LinkerServices;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CompanyResponseView {
    private List<CompanyResponse> companyResponses;

}
