/**
 * CONFIDENTIAL AND PROPRIETARY SOURCE CODE. 
 * 
 * Use and distribution of this code is subject to applicable 
 * licenses and the permission of the code owner. This notice 
 * does not indicate the actual or intended publication of 
 * this source code.
 * 
 * Portions developed for Ho Quoc Tri.
 * 
 * ===== Ho Quoc Tri Modification ===========================================
 * Scope/Bug ID#               ddMMyy  Description
 * 
 *===================================================================
 */
package com.vc.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * DESCRIPTION GOES HERE<br>
 * <br>
 * Copyright © 2020 Ho Quoc Tri. All Rights Reserved.
 *
 * @author hoquoctri
 * @created May 14, 2020
 */
@RestController
@RequestMapping("/img")
public class ImageController {

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/image-view", method = RequestMethod.GET)
	public String imageView() throws IOException {
		return "image-download";
	}

	@RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
	public void getImageAsByteArray(HttpServletResponse response) throws IOException {
		final InputStream in = new FileInputStream("C:\\hoquoctri\\workspaces\\vcare\\vcare\\src\\main\\resources\\config\\img\\lat-vat-phong-khach.jpg");
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		IOUtils.copy(in, response.getOutputStream());
	}

	@RequestMapping(value = "/image-byte-array", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getImageAsByteArray() throws IOException {
		// final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg"); //read file by Context.
		final InputStream in = new FileInputStream("C:\\hoquoctri\\workspaces\\vcare\\vcare\\src\\main\\resources\\config\\img\\lat-vat-phong-khach.jpg"); // read file any where
		return IOUtils.toByteArray(in);
	}

	@RequestMapping(value = "/image-response-entity", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException {
		ResponseEntity<byte[]> responseEntity;
		final HttpHeaders headers = new HttpHeaders();
		// final InputStream in = servletContext.getResourceAsStream("C:\\hoquoctri\\workspaces\\vcare\\vcare\\src\\main\\resources\\config\\img\\lat-vat-phong-khach.jpg");
		final InputStream in = new FileInputStream("C:\\hoquoctri\\workspaces\\vcare\\vcare\\src\\main\\resources\\config\\img\\lat-vat-phong-khach.jpg");
		byte[] media = IOUtils.toByteArray(in);
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/image-resource", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileInputStream> getImageAsResource() {
		final HttpHeaders headers = new HttpHeaders();
		//Resource resource = new ClassPathResource("C:\\hoquoctri\\workspaces\\vcare\\vcare\\src\\main\\resources\\config\\img\\lat-vat-phong-khach.jpg");
		File file = null;
		FileInputStream inputSteam = null;
		try {
			file = ResourceUtils.getFile("C:\\hoquoctri\\workspaces\\vcare\\vcare\\src\\main\\resources\\config\\img\\lat-vat-phong-khach.jpg");
			inputSteam = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
	            .contentType(MediaType.IMAGE_JPEG)
	            .body(inputSteam);
	}
}
