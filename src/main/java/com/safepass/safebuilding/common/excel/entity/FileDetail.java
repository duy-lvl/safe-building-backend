package com.safepass.safebuilding.common.excel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDetail {
    private String fileName;
    private String filePath;
    private String fileExtension;
    private float fileSize;
}
