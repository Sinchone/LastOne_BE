package com.lastone.core.domain.recruitment_img;

import com.lastone.core.domain.recruitment.Recruitment;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    private String imgUrl;

    public RecruitmentImg(Recruitment recruitment, String imgUrl) {
        this.recruitment = recruitment;
        this.imgUrl = imgUrl;
    }

    public RecruitmentImg(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }
}
