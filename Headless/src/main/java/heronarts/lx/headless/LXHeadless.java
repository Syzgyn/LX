/**
 * Copyright 2017- Mark C. Slee, Heron Arts LLC
 *
 * This file is part of the LX Studio software library. By using
 * LX, you agree to the terms of the LX Studio Software License
 * and Distribution Agreement, available at: http://lx.studio/license
 *
 * Please note that the LX license is not open-source. The license
 * allows for free, non-commercial use.
 *
 * HERON ARTS MAKES NO WARRANTY, EXPRESS, IMPLIED, STATUTORY, OR
 * OTHERWISE, AND SPECIFICALLY DISCLAIMS ANY WARRANTY OF
 * MERCHANTABILITY, NON-INFRINGEMENT, OR FITNESS FOR A PARTICULAR
 * PURPOSE, WITH RESPECT TO THE SOFTWARE.
 *
 */

package heronarts.lx.headless;

import java.io.*;
import heronarts.lx.LX;
import heronarts.lx.LXPattern;
import heronarts.lx.model.*;
import heronarts.lx.output.*;
import stratovo.Scale;

/**
 * Example headless CLI for the LX engine. Just write a bit of scaffolding code
 * to load your model, define your outputs, then we're off to the races.
 */
public class LXHeadless {

  public static LXModel buildModel() {
    // TODO: implement code that loads and builds your model here
    return new Scale();
    //return new GridModel(30, 30);
  }

  public static void addSacnOutput(LX lx) throws Exception {
    lx.engine.addOutput(
      new LXDatagramOutput(lx).addDatagram(
        new StreamingACNDatagram(lx.getModel(), 1)
        .setAddress("192.168.0.50")
      )
    );
  }

  public static void addFadeCandyOutput(LX lx) throws Exception {
    lx.engine.addOutput(new FadecandyOutput(lx, "localhost", 9090, lx.getModel()));
  }

  public static void addOPCOutput(LX lx) throws Exception {
    lx.engine.addOutput(new OPCOutput(lx, "localhost", 7890));
  }

  public static void main(String[] args) {
    try {
      LXModel model = buildModel();
      LX lx = new LX(model);
      new LXModel();

      // TODO: add your own output code here
      // addArtNetOutput(lx);
      // addFadecandyOutput(lx);
      //addSacnOutput(lx);
      addOPCOutput(lx);

      // On the CLI you may specify an argument with an .lxp file
      if (args.length > 0) {
        lx.openProject(new File(args[0]));
      } else {
        lx.setPatterns(new LXPattern[] {
          new ExamplePattern(lx)
        });
      }

      lx.engine.start();
    } catch (Exception x) {
      System.err.println(x.getLocalizedMessage());
    }
  }
}
