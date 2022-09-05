package com.malbano.rural.model.dto;

import com.malbano.rural.model.entity.DadosEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.lang.reflect.Type;

@Data
@NoArgsConstructor
@Entity
public class DadosDTO extends DadosEntity {


    public static DadosDTO create(DadosEntity dados) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map((Object) dados, (Type) DadosEntity.class);
    }
}
