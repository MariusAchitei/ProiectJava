package ro.javatraining.jobfinder.jobApplicants;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ro.javatraining.jobfinder.jobApplicants.api.JobApplicantDto;
import ro.javatraining.jobfinder.jobApplicants.api.JobApplicantManagement;
import ro.javatraining.jobfinder.users.api.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicantManagementImpl implements JobApplicantManagement {

    private final JobApplicantRepository jobApplicantRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Long create(JobApplicantDto jobApplicantDto) {
        JobApplicant jobApplicant = modelMapper.map(jobApplicantDto, JobApplicant.class);
        JobApplicant savedApplicant =  jobApplicantRepository.save(jobApplicant);
        return savedApplicant.getId();
    }

    @Override
    public JobApplicantDto getById(Long id) {
        return jobApplicantRepository.findById(id)
                .map(applicant -> modelMapper.map(applicant, JobApplicantDto.class))
                .orElse(null);
    }

    @Override
    public List<JobApplicantDto> getAll() {
        return jobApplicantRepository.findAll().stream()
                .map(jobApplicant -> modelMapper.map(jobApplicant, JobApplicantDto.class))
                .toList();
    }

    @Override
    public void update(Long id, JobApplicantDto updatedJobApplicantDto) {
        jobApplicantRepository.findById(id).ifPresent(jobApplicant -> {
            modelMapper.map(updatedJobApplicantDto, jobApplicant);
            jobApplicantRepository.save(jobApplicant);
        });
    }

    @Override
    public void delete(Long id) {
        jobApplicantRepository.deleteById(id);
    }
}
