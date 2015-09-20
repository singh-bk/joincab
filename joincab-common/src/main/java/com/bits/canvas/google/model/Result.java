package com.bits.canvas.google.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.model.Geometry;

public class Result {

	@SerializedName("formatted_address")
	@Expose
	private String formattedAddress;
	@Expose
	private Geometry geometry;
	@Expose
	private String icon;
	@Expose
	private String id;
	@Expose
	private String name;
	@SerializedName("place_id")
	@Expose
	private String placeId;
	@Expose
	private String reference;
	@Expose
	private List<String> types = new ArrayList<String>();

	/**
	 * 
	 * @return The formattedAddress
	 */
	public String getFormattedAddress() {
		return formattedAddress;
	}

	/**
	 * 
	 * @param formattedAddress
	 *            The formatted_address
	 */
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	/**
	 * 
	 * @return The geometry
	 */
	public Geometry getGeometry() {
		return geometry;
	}

	/**
	 * 
	 * @param geometry
	 *            The geometry
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	/**
	 * 
	 * @return The icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 
	 * @param icon
	 *            The icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 
	 * @return The id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The placeId
	 */
	public String getPlaceId() {
		return placeId;
	}

	/**
	 * 
	 * @param placeId
	 *            The place_id
	 */
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	/**
	 * 
	 * @return The reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            The reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return The types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * 
	 * @param types
	 *            The types
	 */
	public void setTypes(List<String> types) {
		this.types = types;
	}

}
