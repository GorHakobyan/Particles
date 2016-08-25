
precision mediump float;

varying vec3 v_Color;
varying float v_ElapsedTime;

uniform sampler2D u_TextureUnit;

void main()
{
//       For making square patricles appear circle
////    Draw particles as circles (Well, each point will be rendered
////                                with fragments that range from 0 to 1 on each axis relative to gl_PointCoord,
////                                so that places the center of the point at (0.5, 0.5), with 0.5 units of room on
////                                each side. In other words, we can say that the radius of the point is also 0.5.
////                                To draw a circle, all we need to do is draw only the fragments that lie within
////                                that radius)
//    float xDistance = 0.5 - gl_PointCoord.x;
//    float yDistance = 0.5 - gl_PointCoord.y;
//    float distanceFromCenter = sqrt(xDistance * xDistance + yDistance * yDistance);
//
//    if (distanceFromCenter > 0.5)
//    {
//        discard;
//    }
//
//    else
//    {
////    Brighten up young particles and dim old particles by dividing the color by the elapsed time.
//    gl_FragColor = vec4(v_Color / v_ElapsedTime, 1.0);
//    }

    gl_FragColor = vec4(v_Color / v_ElapsedTime, 1.0) * texture2D(u_TextureUnit, gl_PointCoord);
}
