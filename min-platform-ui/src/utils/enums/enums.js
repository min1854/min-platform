
export function getValueByName(enums, name) {
  return byName(enums, name).value;
};

export function getNameByValue(enums, value) {
  return byValue(enums, value).name;
};


export function byName(enums, name) {
  return enums.find(item => item.name === name);
};

export function byValue(enums, value) {
  return enums.find(item => item.value === value);
};

