package com.remuladgryta.hex;

public interface ICoordinate<T> {
	T add(T toAdd);
	T invert();
	T scale(double amount);
}
