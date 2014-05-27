package es.dvdbd.games.chapasrace.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsHelper {

	public static void removeBodySafely(World world, Body body) {
	    //to prevent some obscure c assertion that happened randomly once in a blue moon
	    final Array<JointEdge> list = body.getJointList();
	    while (list.size > 0) {
	        world.destroyJoint(list.get(0).joint);
	    }
	    // actual remove
	    world.destroyBody(body);
	}
}
