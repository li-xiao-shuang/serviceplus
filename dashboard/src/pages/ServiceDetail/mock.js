export default new Array(10).fill("").map((item, index) => ({
  id: index,
  beanName: `firstConfigBean${index}`,
  methodDesc: `firstConfigBean${index}描述`,
  methodSignature: `firstConfigBean${index}签名`,
  params: new Array(3).fill("").map((item, i) => ({
    type: `firstConfigBean${i}参数${index}类型`,
    desc: `firstConfigBean${i}参数${index}描述`,
  })),
}));
