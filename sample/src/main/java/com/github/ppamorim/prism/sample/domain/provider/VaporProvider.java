/*
* Copyright (C) 2015 Pedro Paulo de Amorim.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.ppamorim.prism.sample.domain.provider;

import android.content.Context;
import com.github.ppamorim.prism.sample.R;
import com.github.ppamorim.prism.sample.domain.model.Vapor;
import java.util.ArrayList;

public class VaporProvider {
  public static ArrayList<Vapor> generateVapors(Context context) {
    ArrayList<Vapor> consoles = new ArrayList<>();
    String[] titles = context.getResources().getStringArray(R.array.positions);
    consoles.add(new Vapor(titles[0], "http://31.media.tumblr.com/84d0b0f22170e2b949f92671df2ed81b/"
        + "tumblr_n33hnsqXrO1sey9vmo2_500.gif"));
    consoles.add(new Vapor(titles[1], "http://media.giphy.com/media/6odBqErGYWyNG/giphy.gif"));
    consoles.add(new Vapor(titles[2], "http://33.media.tumblr.com/07d610f83d41d2339c827c748bc11728/"
        + "tumblr_inline_nodct8kSr81rvgpz7_500.gif"));
    consoles.add(new Vapor(titles[3], "http://media.giphy.com/media/pWVCo5ZzQ6iOI/giphy.gif"));
    consoles.add(new Vapor(titles[3], "http://media.giphy.com/media/bXKnukPw6sCd2/giphy.gif"));
    return consoles;
  }
}
