interface IVec3 {
  get_x(): number;

  get_y(): number;

  get_z(): number;
}

class Vec3 implements IVec3 {
  private x: number;
  private y: number;
  private z: number;

  constructor(x: number = 0, y: number = 0, z: number = 0) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  get_x(): number {
    return this.x;
  }

  get_y(): number {
    return this.y;
  }

  get_z(): number {
    return this.z;
  }

  add(o: Vec3): Vec3 {
    return new Vec3(
      o.get_x() + this.x,
      o.get_y() + this.y,
      o.get_z() + this.z
    );
  }

  paramaterized<T>(input:T ): T {
    return input;
  }
}

function a_random_function(message: string): void {
  console.log("A message");
  console.log(message);
}

let new_vec3 = new Vec3(2, 3, 4);
let other_vec3 = new Vec3(1, 1, 1);

let added = new_vec3.add(other_vec3);

console.log(added);

for (let i = 0; i < 10; i++) {
  console.log("i=" + i);
}

let map = new Map;

for (let entry in map.entries()) {
  console.log(entry);
}

const templateString = `some text, expression=${new_vec3} more text`,
      template_exprEnd = `someText ${other_vec3}`,
      template_noExpr = `Hello, world!`

if (template_exprEnd == templateString) {
  console.log("Hello, world!");
}