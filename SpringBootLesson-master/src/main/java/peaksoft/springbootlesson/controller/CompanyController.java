package peaksoft.springbootlesson.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootlesson.dto.CompanyRequest;
import peaksoft.springbootlesson.dto.CompanyResponse;
import peaksoft.springbootlesson.dto.CompanyResponseView;
import peaksoft.springbootlesson.service.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Company Auth",description = "We can create new Company")
@RequestMapping("api/companies")
public class CompanyController {
    private final CompanyService companyService;
    @GetMapping("all")
    @Operation(summary = "Get all companies", description = "Only Admin get all Companies")
    public List<CompanyResponse> getAll(){
        return companyService.getAllCompanies();
    }
    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Admin can get Company by id")
    public CompanyResponse getCompany(@PathVariable("id")Long id){
        return companyService.getCompanyById(id);
    }
    @PostMapping
    @Operation(summary = "Create",description = "Admin can create new Company")
    public CompanyResponse save(@RequestBody CompanyRequest companyRequest){
        return companyService.saveCompany(companyRequest);
    }
    @PutMapping("{id}")
    @Operation(summary = "Update",description = "Admin can update Company")
    public CompanyResponse update(@PathVariable("id")Long id,@RequestBody CompanyRequest request){
        return companyService.updateCompany(id,request);
    }
    @DeleteMapping("{id}")
    @Operation(summary = "Delete",description = "Admin can delete Company by id")
    public String delete(@PathVariable("id")Long id){
        companyService.deleteCompany(id);
        return "Successfully deleted Company with id: "+id;
    }
    @GetMapping
    public CompanyResponseView geAllCompanies(@RequestParam(name = "text",required = false)String text,
                                              @RequestParam int page,
                                              @RequestParam int size){
       return companyService.searchAndPagination(text,page,size);
    }
}
