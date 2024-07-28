package com.klef.project.services;

import java.util.List;

import com.klef.project.models.Winner;

public interface WinnerService {
public void addWinner(Winner w);
public List<Winner> viewWinners();
public void deleteWinner(int wid);
public Winner viewWinnerById(int id);
}
