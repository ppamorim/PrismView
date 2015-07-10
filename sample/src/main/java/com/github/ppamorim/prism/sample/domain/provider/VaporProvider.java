package com.github.ppamorim.prism.sample.domain.provider;

import com.github.ppamorim.prism.sample.domain.model.Vapor;
import java.util.ArrayList;

public class VaporProvider {
  public static ArrayList<Vapor> generateVapors() {
    ArrayList<Vapor> consoles = new ArrayList<>();
    consoles.add(new Vapor("http://31.media.tumblr.com/84d0b0f22170e2b949f92671df2ed81b/"
        + "tumblr_n33hnsqXrO1sey9vmo2_500.gif"));
    consoles.add(new Vapor("http://media.giphy.com/media/6odBqErGYWyNG/giphy.gif"));
    consoles.add(new Vapor("http://33.media.tumblr.com/07d610f83d41d2339c827c748bc11728/"
        + "tumblr_inline_nodct8kSr81rvgpz7_500.gif"));
    consoles.add(new Vapor("http://media.giphy.com/media/pWVCo5ZzQ6iOI/giphy.gif"));
    consoles.add(new Vapor("http://media.giphy.com/media/bXKnukPw6sCd2/giphy.gif"));
    return consoles;
  }
}
