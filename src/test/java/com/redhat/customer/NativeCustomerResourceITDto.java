package com.redhat.customer;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeCustomerResourceITDto extends CustomerDtoResourceTest {

    // Execute the same tests but in native mode.
}