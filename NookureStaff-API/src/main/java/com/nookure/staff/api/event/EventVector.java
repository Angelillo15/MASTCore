package com.nookure.staff.api.event;

import java.lang.reflect.Method;

public record EventVector(Method method, Object listener, NookSubscribe nookSubscribe) {

}
