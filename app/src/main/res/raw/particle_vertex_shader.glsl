
//  A uniform for the projection matrix and a uniform for the current time, so that the shader can
//  figure out how much time has elapsed since each particle was created. We’ll
//  also need four attributes corresponding to the particle’s properties: position,
//  color, direction vector, and creation time.

uniform mat4 u_Matrix;
uniform float u_Time;

attribute vec3 a_Position;
attribute vec3 a_Color;
attribute vec3 a_DirectionVector;
attribute float a_ParticleStartTime;

varying vec3 v_Color;
varying float v_ElapsedTime;

//  It’s important to ensure that we don’t accidentally mess up the w component
//  when doing our math, so we use 3-component vectors to represent the position
//  and the direction, converting to a full 4-component vector only when we need
//  to multiply it with u_Matrix. This ensures that our math above only affects the
//  x, y, and z components.

void main()
{
//  send the color on to the fragment shader
    v_Color = a_Color;

//    calculate how much time has elapsed since this particle was
//    created and send that on to the fragment shader as well
    v_ElapsedTime = u_Time - a_ParticleStartTime;

//    This will calculate an accelerating gravity factor by applying the gravitational
//      acceleration formula and squaring the elapsed time; we also divide things by
//      8 to dampen the effect. The number 8 is arbitrary: we could use any other
//      number that also makes things look good on the screen.
    float gravityFactor = v_ElapsedTime * v_ElapsedTime / 8.0;

//    To calculate the current position of the particle, we multiply the direction vector with the
//    elapsed time and add that to the position. The more time elapses, the further
//    the particle will go.
    vec3 currentPosition = a_Position + (a_DirectionVector * v_ElapsedTime);

    currentPosition.y -= gravityFactor;

//    Project the particle with the matrix
    gl_Position = u_Matrix * vec4(currentPosition, 1.0);

//    Since we’re rendering the particle as a point, we set the point size to 10 pixels.
    gl_PointSize = 25.0;
}
