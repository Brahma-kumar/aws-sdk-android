/*
 * Copyright 2010-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.services.kms.model;

import java.io.Serializable;

import com.amazonaws.AmazonWebServiceRequest;

/**
 * <p>
 * Encrypts plaintext into ciphertext by using a customer master key (CMK). The
 * <code>Encrypt</code> operation has two primary use cases:
 * </p>
 * <ul>
 * <li>
 * <p>
 * You can encrypt small amounts of arbitrary data, such as a personal
 * identifier or database password, or other sensitive information.
 * </p>
 * </li>
 * <li>
 * <p>
 * You can use the <code>Encrypt</code> operation to move encrypted data from
 * one AWS Region to another. For example, in Region A, generate a data key and
 * use the plaintext key to encrypt your data. Then, in Region A, use the
 * <code>Encrypt</code> operation to encrypt the plaintext data key under a CMK
 * in Region B. Now, you can move the encrypted data and the encrypted data key
 * to Region B. When necessary, you can decrypt the encrypted data key and the
 * encrypted data entirely within in Region B.
 * </p>
 * </li>
 * </ul>
 * <p>
 * You don't need to use the <code>Encrypt</code> operation to encrypt a data
 * key. The <a>GenerateDataKey</a> and <a>GenerateDataKeyPair</a> operations
 * return a plaintext data key and an encrypted copy of that data key.
 * </p>
 * <p>
 * When you encrypt data, you must specify a symmetric or asymmetric CMK to use
 * in the encryption operation. The CMK must have a <code>KeyUsage</code> value
 * of <code>ENCRYPT_DECRYPT.</code> To find the <code>KeyUsage</code> of a CMK,
 * use the <a>DescribeKey</a> operation.
 * </p>
 * <p>
 * If you use a symmetric CMK, you can use an encryption context to add
 * additional security to your encryption operation. If you specify an
 * <code>EncryptionContext</code> when encrypting data, you must specify the
 * same encryption context (a case-sensitive exact match) when decrypting the
 * data. Otherwise, the request to decrypt fails with an
 * <code>InvalidCiphertextException</code>. For more information, see <a href=
 * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
 * >Encryption Context</a> in the <i>AWS Key Management Service Developer
 * Guide</i>.
 * </p>
 * <p>
 * If you specify an asymmetric CMK, you must also specify the encryption
 * algorithm. The algorithm must be compatible with the CMK type.
 * </p>
 * <important>
 * <p>
 * When you use an asymmetric CMK to encrypt or reencrypt data, be sure to
 * record the CMK and encryption algorithm that you choose. You will be required
 * to provide the same CMK and encryption algorithm when you decrypt the data.
 * If the CMK and algorithm do not match the values used to encrypt the data,
 * the decrypt operation fails.
 * </p>
 * <p>
 * You are not required to supply the CMK ID and encryption algorithm when you
 * decrypt with symmetric CMKs because AWS KMS stores this information in the
 * ciphertext blob. AWS KMS cannot store metadata in ciphertext generated with
 * asymmetric keys. The standard format for asymmetric key ciphertext does not
 * include configurable fields.
 * </p>
 * </important>
 * <p>
 * The maximum size of the data that you can encrypt varies with the type of CMK
 * and the encryption algorithm that you choose.
 * </p>
 * <ul>
 * <li>
 * <p>
 * Symmetric CMKs
 * </p>
 * <ul>
 * <li>
 * <p>
 * <code>SYMMETRIC_DEFAULT</code>: 4096 bytes
 * </p>
 * </li>
 * </ul>
 * </li>
 * <li>
 * <p>
 * <code>RSA_2048</code>
 * </p>
 * <ul>
 * <li>
 * <p>
 * <code>RSAES_OAEP_SHA_1</code>: 214 bytes
 * </p>
 * </li>
 * <li>
 * <p>
 * <code>RSAES_OAEP_SHA_256</code>: 190 bytes
 * </p>
 * </li>
 * </ul>
 * </li>
 * <li>
 * <p>
 * <code>RSA_3072</code>
 * </p>
 * <ul>
 * <li>
 * <p>
 * <code>RSAES_OAEP_SHA_1</code>: 342 bytes
 * </p>
 * </li>
 * <li>
 * <p>
 * <code>RSAES_OAEP_SHA_256</code>: 318 bytes
 * </p>
 * </li>
 * </ul>
 * </li>
 * <li>
 * <p>
 * <code>RSA_4096</code>
 * </p>
 * <ul>
 * <li>
 * <p>
 * <code>RSAES_OAEP_SHA_1</code>: 470 bytes
 * </p>
 * </li>
 * <li>
 * <p>
 * <code>RSAES_OAEP_SHA_256</code>: 446 bytes
 * </p>
 * </li>
 * </ul>
 * </li>
 * </ul>
 * <p>
 * The CMK that you use for this operation must be in a compatible key state.
 * For details, see <a
 * href="https://docs.aws.amazon.com/kms/latest/developerguide/key-state.html"
 * >How Key State Affects Use of a Customer Master Key</a> in the <i>AWS Key
 * Management Service Developer Guide</i>.
 * </p>
 * <p>
 * <b>Cross-account use</b>: Yes. To perform this operation with a CMK in a
 * different AWS account, specify the key ARN or alias ARN in the value of the
 * <code>KeyId</code> parameter.
 * </p>
 * <p>
 * <b>Required permissions</b>: <a href=
 * "https://docs.aws.amazon.com/kms/latest/developerguide/kms-api-permissions-reference.html"
 * >kms:Encrypt</a> (key policy)
 * </p>
 * <p>
 * <b>Related operations:</b>
 * </p>
 * <ul>
 * <li>
 * <p>
 * <a>Decrypt</a>
 * </p>
 * </li>
 * <li>
 * <p>
 * <a>GenerateDataKey</a>
 * </p>
 * </li>
 * <li>
 * <p>
 * <a>GenerateDataKeyPair</a>
 * </p>
 * </li>
 * </ul>
 */
public class EncryptRequest extends AmazonWebServiceRequest implements Serializable {
    /**
     * <p>
     * A unique identifier for the customer master key (CMK).
     * </p>
     * <p>
     * To specify a CMK, use its key ID, Amazon Resource Name (ARN), alias name,
     * or alias ARN. When using an alias name, prefix it with
     * <code>"alias/"</code>. To specify a CMK in a different AWS account, you
     * must use the key ARN or alias ARN.
     * </p>
     * <p>
     * For example:
     * </p>
     * <ul>
     * <li>
     * <p>
     * Key ID: <code>1234abcd-12ab-34cd-56ef-1234567890ab</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Key ARN:
     * <code>arn:aws:kms:us-east-2:111122223333:key/1234abcd-12ab-34cd-56ef-1234567890ab</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Alias name: <code>alias/ExampleAlias</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Alias ARN:
     * <code>arn:aws:kms:us-east-2:111122223333:alias/ExampleAlias</code>
     * </p>
     * </li>
     * </ul>
     * <p>
     * To get the key ID and key ARN for a CMK, use <a>ListKeys</a> or
     * <a>DescribeKey</a>. To get the alias name and alias ARN, use
     * <a>ListAliases</a>.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Length: </b>1 - 2048<br/>
     */
    private String keyId;

    /**
     * <p>
     * Data to be encrypted.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Length: </b>1 - 4096<br/>
     */
    private java.nio.ByteBuffer plaintext;

    /**
     * <p>
     * Specifies the encryption context that will be used to encrypt the data.
     * An encryption context is valid only for <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#cryptographic-operations"
     * >cryptographic operations</a> with a symmetric CMK. The standard
     * asymmetric encryption algorithms that AWS KMS uses do not support an
     * encryption context.
     * </p>
     * <p>
     * An <i>encryption context</i> is a collection of non-secret key-value
     * pairs that represents additional authenticated data. When you use an
     * encryption context to encrypt data, you must specify the same (an exact
     * case-sensitive match) encryption context to decrypt the data. An
     * encryption context is optional when encrypting with a symmetric CMK, but
     * it is highly recommended.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
     * >Encryption Context</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     */
    private java.util.Map<String, String> encryptionContext = new java.util.HashMap<String, String>();

    /**
     * <p>
     * A list of grant tokens.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     * >Grant Tokens</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     */
    private java.util.List<String> grantTokens = new java.util.ArrayList<String>();

    /**
     * <p>
     * Specifies the encryption algorithm that AWS KMS will use to encrypt the
     * plaintext message. The algorithm must be compatible with the CMK that you
     * specify.
     * </p>
     * <p>
     * This parameter is required only for asymmetric CMKs. The default value,
     * <code>SYMMETRIC_DEFAULT</code>, is the algorithm used for symmetric CMKs.
     * If you are using an asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Allowed Values: </b>SYMMETRIC_DEFAULT, RSAES_OAEP_SHA_1,
     * RSAES_OAEP_SHA_256
     */
    private String encryptionAlgorithm;

    /**
     * <p>
     * A unique identifier for the customer master key (CMK).
     * </p>
     * <p>
     * To specify a CMK, use its key ID, Amazon Resource Name (ARN), alias name,
     * or alias ARN. When using an alias name, prefix it with
     * <code>"alias/"</code>. To specify a CMK in a different AWS account, you
     * must use the key ARN or alias ARN.
     * </p>
     * <p>
     * For example:
     * </p>
     * <ul>
     * <li>
     * <p>
     * Key ID: <code>1234abcd-12ab-34cd-56ef-1234567890ab</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Key ARN:
     * <code>arn:aws:kms:us-east-2:111122223333:key/1234abcd-12ab-34cd-56ef-1234567890ab</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Alias name: <code>alias/ExampleAlias</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Alias ARN:
     * <code>arn:aws:kms:us-east-2:111122223333:alias/ExampleAlias</code>
     * </p>
     * </li>
     * </ul>
     * <p>
     * To get the key ID and key ARN for a CMK, use <a>ListKeys</a> or
     * <a>DescribeKey</a>. To get the alias name and alias ARN, use
     * <a>ListAliases</a>.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Length: </b>1 - 2048<br/>
     *
     * @return <p>
     *         A unique identifier for the customer master key (CMK).
     *         </p>
     *         <p>
     *         To specify a CMK, use its key ID, Amazon Resource Name (ARN),
     *         alias name, or alias ARN. When using an alias name, prefix it
     *         with <code>"alias/"</code>. To specify a CMK in a different AWS
     *         account, you must use the key ARN or alias ARN.
     *         </p>
     *         <p>
     *         For example:
     *         </p>
     *         <ul>
     *         <li>
     *         <p>
     *         Key ID: <code>1234abcd-12ab-34cd-56ef-1234567890ab</code>
     *         </p>
     *         </li>
     *         <li>
     *         <p>
     *         Key ARN:
     *         <code>arn:aws:kms:us-east-2:111122223333:key/1234abcd-12ab-34cd-56ef-1234567890ab</code>
     *         </p>
     *         </li>
     *         <li>
     *         <p>
     *         Alias name: <code>alias/ExampleAlias</code>
     *         </p>
     *         </li>
     *         <li>
     *         <p>
     *         Alias ARN:
     *         <code>arn:aws:kms:us-east-2:111122223333:alias/ExampleAlias</code>
     *         </p>
     *         </li>
     *         </ul>
     *         <p>
     *         To get the key ID and key ARN for a CMK, use <a>ListKeys</a> or
     *         <a>DescribeKey</a>. To get the alias name and alias ARN, use
     *         <a>ListAliases</a>.
     *         </p>
     */
    public String getKeyId() {
        return keyId;
    }

    /**
     * <p>
     * A unique identifier for the customer master key (CMK).
     * </p>
     * <p>
     * To specify a CMK, use its key ID, Amazon Resource Name (ARN), alias name,
     * or alias ARN. When using an alias name, prefix it with
     * <code>"alias/"</code>. To specify a CMK in a different AWS account, you
     * must use the key ARN or alias ARN.
     * </p>
     * <p>
     * For example:
     * </p>
     * <ul>
     * <li>
     * <p>
     * Key ID: <code>1234abcd-12ab-34cd-56ef-1234567890ab</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Key ARN:
     * <code>arn:aws:kms:us-east-2:111122223333:key/1234abcd-12ab-34cd-56ef-1234567890ab</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Alias name: <code>alias/ExampleAlias</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Alias ARN:
     * <code>arn:aws:kms:us-east-2:111122223333:alias/ExampleAlias</code>
     * </p>
     * </li>
     * </ul>
     * <p>
     * To get the key ID and key ARN for a CMK, use <a>ListKeys</a> or
     * <a>DescribeKey</a>. To get the alias name and alias ARN, use
     * <a>ListAliases</a>.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Length: </b>1 - 2048<br/>
     *
     * @param keyId <p>
     *            A unique identifier for the customer master key (CMK).
     *            </p>
     *            <p>
     *            To specify a CMK, use its key ID, Amazon Resource Name (ARN),
     *            alias name, or alias ARN. When using an alias name, prefix it
     *            with <code>"alias/"</code>. To specify a CMK in a different
     *            AWS account, you must use the key ARN or alias ARN.
     *            </p>
     *            <p>
     *            For example:
     *            </p>
     *            <ul>
     *            <li>
     *            <p>
     *            Key ID: <code>1234abcd-12ab-34cd-56ef-1234567890ab</code>
     *            </p>
     *            </li>
     *            <li>
     *            <p>
     *            Key ARN:
     *            <code>arn:aws:kms:us-east-2:111122223333:key/1234abcd-12ab-34cd-56ef-1234567890ab</code>
     *            </p>
     *            </li>
     *            <li>
     *            <p>
     *            Alias name: <code>alias/ExampleAlias</code>
     *            </p>
     *            </li>
     *            <li>
     *            <p>
     *            Alias ARN:
     *            <code>arn:aws:kms:us-east-2:111122223333:alias/ExampleAlias</code>
     *            </p>
     *            </li>
     *            </ul>
     *            <p>
     *            To get the key ID and key ARN for a CMK, use <a>ListKeys</a>
     *            or <a>DescribeKey</a>. To get the alias name and alias ARN,
     *            use <a>ListAliases</a>.
     *            </p>
     */
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    /**
     * <p>
     * A unique identifier for the customer master key (CMK).
     * </p>
     * <p>
     * To specify a CMK, use its key ID, Amazon Resource Name (ARN), alias name,
     * or alias ARN. When using an alias name, prefix it with
     * <code>"alias/"</code>. To specify a CMK in a different AWS account, you
     * must use the key ARN or alias ARN.
     * </p>
     * <p>
     * For example:
     * </p>
     * <ul>
     * <li>
     * <p>
     * Key ID: <code>1234abcd-12ab-34cd-56ef-1234567890ab</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Key ARN:
     * <code>arn:aws:kms:us-east-2:111122223333:key/1234abcd-12ab-34cd-56ef-1234567890ab</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Alias name: <code>alias/ExampleAlias</code>
     * </p>
     * </li>
     * <li>
     * <p>
     * Alias ARN:
     * <code>arn:aws:kms:us-east-2:111122223333:alias/ExampleAlias</code>
     * </p>
     * </li>
     * </ul>
     * <p>
     * To get the key ID and key ARN for a CMK, use <a>ListKeys</a> or
     * <a>DescribeKey</a>. To get the alias name and alias ARN, use
     * <a>ListAliases</a>.
     * </p>
     * <p>
     * Returns a reference to this object so that method calls can be chained
     * together.
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Length: </b>1 - 2048<br/>
     *
     * @param keyId <p>
     *            A unique identifier for the customer master key (CMK).
     *            </p>
     *            <p>
     *            To specify a CMK, use its key ID, Amazon Resource Name (ARN),
     *            alias name, or alias ARN. When using an alias name, prefix it
     *            with <code>"alias/"</code>. To specify a CMK in a different
     *            AWS account, you must use the key ARN or alias ARN.
     *            </p>
     *            <p>
     *            For example:
     *            </p>
     *            <ul>
     *            <li>
     *            <p>
     *            Key ID: <code>1234abcd-12ab-34cd-56ef-1234567890ab</code>
     *            </p>
     *            </li>
     *            <li>
     *            <p>
     *            Key ARN:
     *            <code>arn:aws:kms:us-east-2:111122223333:key/1234abcd-12ab-34cd-56ef-1234567890ab</code>
     *            </p>
     *            </li>
     *            <li>
     *            <p>
     *            Alias name: <code>alias/ExampleAlias</code>
     *            </p>
     *            </li>
     *            <li>
     *            <p>
     *            Alias ARN:
     *            <code>arn:aws:kms:us-east-2:111122223333:alias/ExampleAlias</code>
     *            </p>
     *            </li>
     *            </ul>
     *            <p>
     *            To get the key ID and key ARN for a CMK, use <a>ListKeys</a>
     *            or <a>DescribeKey</a>. To get the alias name and alias ARN,
     *            use <a>ListAliases</a>.
     *            </p>
     * @return A reference to this updated object so that method calls can be
     *         chained together.
     */
    public EncryptRequest withKeyId(String keyId) {
        this.keyId = keyId;
        return this;
    }

    /**
     * <p>
     * Data to be encrypted.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Length: </b>1 - 4096<br/>
     *
     * @return <p>
     *         Data to be encrypted.
     *         </p>
     */
    public java.nio.ByteBuffer getPlaintext() {
        return plaintext;
    }

    /**
     * <p>
     * Data to be encrypted.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Length: </b>1 - 4096<br/>
     *
     * @param plaintext <p>
     *            Data to be encrypted.
     *            </p>
     */
    public void setPlaintext(java.nio.ByteBuffer plaintext) {
        this.plaintext = plaintext;
    }

    /**
     * <p>
     * Data to be encrypted.
     * </p>
     * <p>
     * Returns a reference to this object so that method calls can be chained
     * together.
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Length: </b>1 - 4096<br/>
     *
     * @param plaintext <p>
     *            Data to be encrypted.
     *            </p>
     * @return A reference to this updated object so that method calls can be
     *         chained together.
     */
    public EncryptRequest withPlaintext(java.nio.ByteBuffer plaintext) {
        this.plaintext = plaintext;
        return this;
    }

    /**
     * <p>
     * Specifies the encryption context that will be used to encrypt the data.
     * An encryption context is valid only for <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#cryptographic-operations"
     * >cryptographic operations</a> with a symmetric CMK. The standard
     * asymmetric encryption algorithms that AWS KMS uses do not support an
     * encryption context.
     * </p>
     * <p>
     * An <i>encryption context</i> is a collection of non-secret key-value
     * pairs that represents additional authenticated data. When you use an
     * encryption context to encrypt data, you must specify the same (an exact
     * case-sensitive match) encryption context to decrypt the data. An
     * encryption context is optional when encrypting with a symmetric CMK, but
     * it is highly recommended.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
     * >Encryption Context</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     *
     * @return <p>
     *         Specifies the encryption context that will be used to encrypt the
     *         data. An encryption context is valid only for <a href=
     *         "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#cryptographic-operations"
     *         >cryptographic operations</a> with a symmetric CMK. The standard
     *         asymmetric encryption algorithms that AWS KMS uses do not support
     *         an encryption context.
     *         </p>
     *         <p>
     *         An <i>encryption context</i> is a collection of non-secret
     *         key-value pairs that represents additional authenticated data.
     *         When you use an encryption context to encrypt data, you must
     *         specify the same (an exact case-sensitive match) encryption
     *         context to decrypt the data. An encryption context is optional
     *         when encrypting with a symmetric CMK, but it is highly
     *         recommended.
     *         </p>
     *         <p>
     *         For more information, see <a href=
     *         "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
     *         >Encryption Context</a> in the <i>AWS Key Management Service
     *         Developer Guide</i>.
     *         </p>
     */
    public java.util.Map<String, String> getEncryptionContext() {
        return encryptionContext;
    }

    /**
     * <p>
     * Specifies the encryption context that will be used to encrypt the data.
     * An encryption context is valid only for <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#cryptographic-operations"
     * >cryptographic operations</a> with a symmetric CMK. The standard
     * asymmetric encryption algorithms that AWS KMS uses do not support an
     * encryption context.
     * </p>
     * <p>
     * An <i>encryption context</i> is a collection of non-secret key-value
     * pairs that represents additional authenticated data. When you use an
     * encryption context to encrypt data, you must specify the same (an exact
     * case-sensitive match) encryption context to decrypt the data. An
     * encryption context is optional when encrypting with a symmetric CMK, but
     * it is highly recommended.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
     * >Encryption Context</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     *
     * @param encryptionContext <p>
     *            Specifies the encryption context that will be used to encrypt
     *            the data. An encryption context is valid only for <a href=
     *            "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#cryptographic-operations"
     *            >cryptographic operations</a> with a symmetric CMK. The
     *            standard asymmetric encryption algorithms that AWS KMS uses do
     *            not support an encryption context.
     *            </p>
     *            <p>
     *            An <i>encryption context</i> is a collection of non-secret
     *            key-value pairs that represents additional authenticated data.
     *            When you use an encryption context to encrypt data, you must
     *            specify the same (an exact case-sensitive match) encryption
     *            context to decrypt the data. An encryption context is optional
     *            when encrypting with a symmetric CMK, but it is highly
     *            recommended.
     *            </p>
     *            <p>
     *            For more information, see <a href=
     *            "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
     *            >Encryption Context</a> in the <i>AWS Key Management Service
     *            Developer Guide</i>.
     *            </p>
     */
    public void setEncryptionContext(java.util.Map<String, String> encryptionContext) {
        this.encryptionContext = encryptionContext;
    }

    /**
     * <p>
     * Specifies the encryption context that will be used to encrypt the data.
     * An encryption context is valid only for <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#cryptographic-operations"
     * >cryptographic operations</a> with a symmetric CMK. The standard
     * asymmetric encryption algorithms that AWS KMS uses do not support an
     * encryption context.
     * </p>
     * <p>
     * An <i>encryption context</i> is a collection of non-secret key-value
     * pairs that represents additional authenticated data. When you use an
     * encryption context to encrypt data, you must specify the same (an exact
     * case-sensitive match) encryption context to decrypt the data. An
     * encryption context is optional when encrypting with a symmetric CMK, but
     * it is highly recommended.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
     * >Encryption Context</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     * <p>
     * Returns a reference to this object so that method calls can be chained
     * together.
     *
     * @param encryptionContext <p>
     *            Specifies the encryption context that will be used to encrypt
     *            the data. An encryption context is valid only for <a href=
     *            "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#cryptographic-operations"
     *            >cryptographic operations</a> with a symmetric CMK. The
     *            standard asymmetric encryption algorithms that AWS KMS uses do
     *            not support an encryption context.
     *            </p>
     *            <p>
     *            An <i>encryption context</i> is a collection of non-secret
     *            key-value pairs that represents additional authenticated data.
     *            When you use an encryption context to encrypt data, you must
     *            specify the same (an exact case-sensitive match) encryption
     *            context to decrypt the data. An encryption context is optional
     *            when encrypting with a symmetric CMK, but it is highly
     *            recommended.
     *            </p>
     *            <p>
     *            For more information, see <a href=
     *            "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
     *            >Encryption Context</a> in the <i>AWS Key Management Service
     *            Developer Guide</i>.
     *            </p>
     * @return A reference to this updated object so that method calls can be
     *         chained together.
     */
    public EncryptRequest withEncryptionContext(java.util.Map<String, String> encryptionContext) {
        this.encryptionContext = encryptionContext;
        return this;
    }

    /**
     * <p>
     * Specifies the encryption context that will be used to encrypt the data.
     * An encryption context is valid only for <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#cryptographic-operations"
     * >cryptographic operations</a> with a symmetric CMK. The standard
     * asymmetric encryption algorithms that AWS KMS uses do not support an
     * encryption context.
     * </p>
     * <p>
     * An <i>encryption context</i> is a collection of non-secret key-value
     * pairs that represents additional authenticated data. When you use an
     * encryption context to encrypt data, you must specify the same (an exact
     * case-sensitive match) encryption context to decrypt the data. An
     * encryption context is optional when encrypting with a symmetric CMK, but
     * it is highly recommended.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#encrypt_context"
     * >Encryption Context</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     * <p>
     * The method adds a new key-value pair into EncryptionContext parameter,
     * and returns a reference to this object so that method calls can be
     * chained together.
     *
     * @param key The key of the entry to be added into EncryptionContext.
     * @param value The corresponding value of the entry to be added into
     *            EncryptionContext.
     * @return A reference to this updated object so that method calls can be
     *         chained together.
     */
    public EncryptRequest addEncryptionContextEntry(String key, String value) {
        if (null == this.encryptionContext) {
            this.encryptionContext = new java.util.HashMap<String, String>();
        }
        if (this.encryptionContext.containsKey(key))
            throw new IllegalArgumentException("Duplicated keys (" + key.toString()
                    + ") are provided.");
        this.encryptionContext.put(key, value);
        return this;
    }

    /**
     * Removes all the entries added into EncryptionContext.
     * <p>
     * Returns a reference to this object so that method calls can be chained
     * together.
     */
    public EncryptRequest clearEncryptionContextEntries() {
        this.encryptionContext = null;
        return this;
    }

    /**
     * <p>
     * A list of grant tokens.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     * >Grant Tokens</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     *
     * @return <p>
     *         A list of grant tokens.
     *         </p>
     *         <p>
     *         For more information, see <a href=
     *         "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     *         >Grant Tokens</a> in the <i>AWS Key Management Service Developer
     *         Guide</i>.
     *         </p>
     */
    public java.util.List<String> getGrantTokens() {
        return grantTokens;
    }

    /**
     * <p>
     * A list of grant tokens.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     * >Grant Tokens</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     *
     * @param grantTokens <p>
     *            A list of grant tokens.
     *            </p>
     *            <p>
     *            For more information, see <a href=
     *            "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     *            >Grant Tokens</a> in the <i>AWS Key Management Service
     *            Developer Guide</i>.
     *            </p>
     */
    public void setGrantTokens(java.util.Collection<String> grantTokens) {
        if (grantTokens == null) {
            this.grantTokens = null;
            return;
        }

        this.grantTokens = new java.util.ArrayList<String>(grantTokens);
    }

    /**
     * <p>
     * A list of grant tokens.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     * >Grant Tokens</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     * <p>
     * Returns a reference to this object so that method calls can be chained
     * together.
     *
     * @param grantTokens <p>
     *            A list of grant tokens.
     *            </p>
     *            <p>
     *            For more information, see <a href=
     *            "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     *            >Grant Tokens</a> in the <i>AWS Key Management Service
     *            Developer Guide</i>.
     *            </p>
     * @return A reference to this updated object so that method calls can be
     *         chained together.
     */
    public EncryptRequest withGrantTokens(String... grantTokens) {
        if (getGrantTokens() == null) {
            this.grantTokens = new java.util.ArrayList<String>(grantTokens.length);
        }
        for (String value : grantTokens) {
            this.grantTokens.add(value);
        }
        return this;
    }

    /**
     * <p>
     * A list of grant tokens.
     * </p>
     * <p>
     * For more information, see <a href=
     * "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     * >Grant Tokens</a> in the <i>AWS Key Management Service Developer
     * Guide</i>.
     * </p>
     * <p>
     * Returns a reference to this object so that method calls can be chained
     * together.
     *
     * @param grantTokens <p>
     *            A list of grant tokens.
     *            </p>
     *            <p>
     *            For more information, see <a href=
     *            "https://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#grant_token"
     *            >Grant Tokens</a> in the <i>AWS Key Management Service
     *            Developer Guide</i>.
     *            </p>
     * @return A reference to this updated object so that method calls can be
     *         chained together.
     */
    public EncryptRequest withGrantTokens(java.util.Collection<String> grantTokens) {
        setGrantTokens(grantTokens);
        return this;
    }

    /**
     * <p>
     * Specifies the encryption algorithm that AWS KMS will use to encrypt the
     * plaintext message. The algorithm must be compatible with the CMK that you
     * specify.
     * </p>
     * <p>
     * This parameter is required only for asymmetric CMKs. The default value,
     * <code>SYMMETRIC_DEFAULT</code>, is the algorithm used for symmetric CMKs.
     * If you are using an asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Allowed Values: </b>SYMMETRIC_DEFAULT, RSAES_OAEP_SHA_1,
     * RSAES_OAEP_SHA_256
     *
     * @return <p>
     *         Specifies the encryption algorithm that AWS KMS will use to
     *         encrypt the plaintext message. The algorithm must be compatible
     *         with the CMK that you specify.
     *         </p>
     *         <p>
     *         This parameter is required only for asymmetric CMKs. The default
     *         value, <code>SYMMETRIC_DEFAULT</code>, is the algorithm used for
     *         symmetric CMKs. If you are using an asymmetric CMK, we recommend
     *         RSAES_OAEP_SHA_256.
     *         </p>
     * @see EncryptionAlgorithmSpec
     */
    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    /**
     * <p>
     * Specifies the encryption algorithm that AWS KMS will use to encrypt the
     * plaintext message. The algorithm must be compatible with the CMK that you
     * specify.
     * </p>
     * <p>
     * This parameter is required only for asymmetric CMKs. The default value,
     * <code>SYMMETRIC_DEFAULT</code>, is the algorithm used for symmetric CMKs.
     * If you are using an asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Allowed Values: </b>SYMMETRIC_DEFAULT, RSAES_OAEP_SHA_1,
     * RSAES_OAEP_SHA_256
     *
     * @param encryptionAlgorithm <p>
     *            Specifies the encryption algorithm that AWS KMS will use to
     *            encrypt the plaintext message. The algorithm must be
     *            compatible with the CMK that you specify.
     *            </p>
     *            <p>
     *            This parameter is required only for asymmetric CMKs. The
     *            default value, <code>SYMMETRIC_DEFAULT</code>, is the
     *            algorithm used for symmetric CMKs. If you are using an
     *            asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     *            </p>
     * @see EncryptionAlgorithmSpec
     */
    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    /**
     * <p>
     * Specifies the encryption algorithm that AWS KMS will use to encrypt the
     * plaintext message. The algorithm must be compatible with the CMK that you
     * specify.
     * </p>
     * <p>
     * This parameter is required only for asymmetric CMKs. The default value,
     * <code>SYMMETRIC_DEFAULT</code>, is the algorithm used for symmetric CMKs.
     * If you are using an asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     * </p>
     * <p>
     * Returns a reference to this object so that method calls can be chained
     * together.
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Allowed Values: </b>SYMMETRIC_DEFAULT, RSAES_OAEP_SHA_1,
     * RSAES_OAEP_SHA_256
     *
     * @param encryptionAlgorithm <p>
     *            Specifies the encryption algorithm that AWS KMS will use to
     *            encrypt the plaintext message. The algorithm must be
     *            compatible with the CMK that you specify.
     *            </p>
     *            <p>
     *            This parameter is required only for asymmetric CMKs. The
     *            default value, <code>SYMMETRIC_DEFAULT</code>, is the
     *            algorithm used for symmetric CMKs. If you are using an
     *            asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     *            </p>
     * @return A reference to this updated object so that method calls can be
     *         chained together.
     * @see EncryptionAlgorithmSpec
     */
    public EncryptRequest withEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        return this;
    }

    /**
     * <p>
     * Specifies the encryption algorithm that AWS KMS will use to encrypt the
     * plaintext message. The algorithm must be compatible with the CMK that you
     * specify.
     * </p>
     * <p>
     * This parameter is required only for asymmetric CMKs. The default value,
     * <code>SYMMETRIC_DEFAULT</code>, is the algorithm used for symmetric CMKs.
     * If you are using an asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     * </p>
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Allowed Values: </b>SYMMETRIC_DEFAULT, RSAES_OAEP_SHA_1,
     * RSAES_OAEP_SHA_256
     *
     * @param encryptionAlgorithm <p>
     *            Specifies the encryption algorithm that AWS KMS will use to
     *            encrypt the plaintext message. The algorithm must be
     *            compatible with the CMK that you specify.
     *            </p>
     *            <p>
     *            This parameter is required only for asymmetric CMKs. The
     *            default value, <code>SYMMETRIC_DEFAULT</code>, is the
     *            algorithm used for symmetric CMKs. If you are using an
     *            asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     *            </p>
     * @see EncryptionAlgorithmSpec
     */
    public void setEncryptionAlgorithm(EncryptionAlgorithmSpec encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm.toString();
    }

    /**
     * <p>
     * Specifies the encryption algorithm that AWS KMS will use to encrypt the
     * plaintext message. The algorithm must be compatible with the CMK that you
     * specify.
     * </p>
     * <p>
     * This parameter is required only for asymmetric CMKs. The default value,
     * <code>SYMMETRIC_DEFAULT</code>, is the algorithm used for symmetric CMKs.
     * If you are using an asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     * </p>
     * <p>
     * Returns a reference to this object so that method calls can be chained
     * together.
     * <p>
     * <b>Constraints:</b><br/>
     * <b>Allowed Values: </b>SYMMETRIC_DEFAULT, RSAES_OAEP_SHA_1,
     * RSAES_OAEP_SHA_256
     *
     * @param encryptionAlgorithm <p>
     *            Specifies the encryption algorithm that AWS KMS will use to
     *            encrypt the plaintext message. The algorithm must be
     *            compatible with the CMK that you specify.
     *            </p>
     *            <p>
     *            This parameter is required only for asymmetric CMKs. The
     *            default value, <code>SYMMETRIC_DEFAULT</code>, is the
     *            algorithm used for symmetric CMKs. If you are using an
     *            asymmetric CMK, we recommend RSAES_OAEP_SHA_256.
     *            </p>
     * @return A reference to this updated object so that method calls can be
     *         chained together.
     * @see EncryptionAlgorithmSpec
     */
    public EncryptRequest withEncryptionAlgorithm(EncryptionAlgorithmSpec encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm.toString();
        return this;
    }

    /**
     * Returns a string representation of this object; useful for testing and
     * debugging.
     *
     * @return A string representation of this object.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getKeyId() != null)
            sb.append("KeyId: " + getKeyId() + ",");
        if (getPlaintext() != null)
            sb.append("Plaintext: " + getPlaintext() + ",");
        if (getEncryptionContext() != null)
            sb.append("EncryptionContext: " + getEncryptionContext() + ",");
        if (getGrantTokens() != null)
            sb.append("GrantTokens: " + getGrantTokens() + ",");
        if (getEncryptionAlgorithm() != null)
            sb.append("EncryptionAlgorithm: " + getEncryptionAlgorithm());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getKeyId() == null) ? 0 : getKeyId().hashCode());
        hashCode = prime * hashCode + ((getPlaintext() == null) ? 0 : getPlaintext().hashCode());
        hashCode = prime * hashCode
                + ((getEncryptionContext() == null) ? 0 : getEncryptionContext().hashCode());
        hashCode = prime * hashCode
                + ((getGrantTokens() == null) ? 0 : getGrantTokens().hashCode());
        hashCode = prime * hashCode
                + ((getEncryptionAlgorithm() == null) ? 0 : getEncryptionAlgorithm().hashCode());
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof EncryptRequest == false)
            return false;
        EncryptRequest other = (EncryptRequest) obj;

        if (other.getKeyId() == null ^ this.getKeyId() == null)
            return false;
        if (other.getKeyId() != null && other.getKeyId().equals(this.getKeyId()) == false)
            return false;
        if (other.getPlaintext() == null ^ this.getPlaintext() == null)
            return false;
        if (other.getPlaintext() != null
                && other.getPlaintext().equals(this.getPlaintext()) == false)
            return false;
        if (other.getEncryptionContext() == null ^ this.getEncryptionContext() == null)
            return false;
        if (other.getEncryptionContext() != null
                && other.getEncryptionContext().equals(this.getEncryptionContext()) == false)
            return false;
        if (other.getGrantTokens() == null ^ this.getGrantTokens() == null)
            return false;
        if (other.getGrantTokens() != null
                && other.getGrantTokens().equals(this.getGrantTokens()) == false)
            return false;
        if (other.getEncryptionAlgorithm() == null ^ this.getEncryptionAlgorithm() == null)
            return false;
        if (other.getEncryptionAlgorithm() != null
                && other.getEncryptionAlgorithm().equals(this.getEncryptionAlgorithm()) == false)
            return false;
        return true;
    }
}
