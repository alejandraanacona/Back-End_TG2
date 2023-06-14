package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Videos;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepository extends PagingAndSortingRepository<Videos, Long> {
}
